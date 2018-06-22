package com.bjfl.galaxymessage.jobs;

import com.bjfl.galaxymessage.messages.*;
import com.bjfl.galaxymessage.mqtt.MqttSender;
import com.bjfl.galaxymessage.netty.NettyMessageHandler;
import com.bjfl.galaxymessage.parser.MessageType;
import com.bjfl.galaxymessage.util.Constants;
import com.bjfl.galaxymessage.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 所有任务都提交到任务总线，用于底层反馈溯源，及解析下一步任务
 *
 * 任务类型1： 有下一步操作的任务(先查货道再执行的指令)，findNext, 通过job是否有nextMessage来判断，有则调用并执行。
 * 同时修改该job，将nextMessage置空
 * 该类型任务只能顺序执行，无法处理网络异步的情况。
 *
 * 任务类型2： 操作都执行完了，需要返回给上层，通过是否有nextMessage来判断，无则解析MessageType与mqRequest中的theme，将任务传递给mqttSender
 */
@Component
public class JobBus {

    @Autowired
    NettyMessageHandler nettyMessageHandler;
    @Autowired
    MqttSender mqttSender;

    Logger logger = LoggerFactory.getLogger(JobBus.class);

    public static Map<String, CopyOnWriteArrayList<Job>> jobs = new ConcurrentHashMap<>();

    public void push(String machineNo, Job job) {
        jobs.get(machineNo).add(job);
    }

    public void doNext(CellStatusMessage cellStatusMessage) {
        logger.info("job bus do next:" +cellStatusMessage.toString());
        String machineNo = cellStatusMessage.getMachineCode(Constants.NORMAL_MESSAGE_MACHINE_CODE_OFFSET);
        CopyOnWriteArrayList<Job> jobs = JobBus.jobs.get(machineNo);
        for (Job job : jobs) {
            logger.info("jobs foreach");
            if (!job.isExpired() && job.getNextMessage()!=null) { // 有下一步
                logger.info("找到一个job："+job.toString());
                // 暂时忽略查询货道状态解析发现不能出货的情况
                nettyMessageHandler.channelWrite(NettyMessageHandler.clientList.get(machineNo), job.getNextMessage());
                // 如果发出的命令是整体出货，则在两秒后开始轮询出货结果
                if (job.getNextMessage() instanceof ShipmentMessage) {
                    try {
                        Thread.sleep(2000);
                        ShipmentResultMessage shipmentResultMessage = new ShipmentResultMessage();
                        int position = job.getNextMessage().getPosition();
                        shipmentResultMessage.generate(machineNo, position, MessageType.SHIPMENT_RESULT.getCode(), Arrays.asList(0x00, 0x01, 0x00));
                        job.setNextMessage(shipmentResultMessage);
                        nettyMessageHandler.channelWrite(NettyMessageHandler.clientList.get(machineNo), shipmentResultMessage);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else {
                    jobs.remove(job);
                }
                return;
            }
        }
        for (Job job : jobs) {
            if (!job.isExpired() && job.getNextMessage() ==null) { // 无下一步，直接向上层返回货道状态
                mqttSender.sendCellStatus(cellStatusMessage, job.getMqRequest().getTheme());
                jobs.remove(job);
                break;
            }
        }
    }

    public void doBack(Message msg) {

        String machineNo = msg.getMachineCode(Constants.NORMAL_MESSAGE_MACHINE_CODE_OFFSET);
        CopyOnWriteArrayList<Job> jobs = JobBus.jobs.get(machineNo);

        if (msg instanceof ShipmentMessage) {
            return;
        }

        if (msg instanceof PreposeMotorCaseMessage) {
            return;
        }

        if (msg instanceof PreposeMotorHomeMessage) {
            return;
        }

        if (msg instanceof CoorDinateCaseMessage) {
            return;
        }

        if (msg instanceof CoorDinateHomeMessage) {
            return;
        }

        if (msg instanceof ResetMessage) {
            return;
        }

        if (msg instanceof ShipmentLogMessage) {
            return;
        }

        if (msg instanceof ShipmentResultMessage) {
            logger.info("收到一个出货结果");
            for (Job job : jobs) {
                if (!job.isExpired() && job.getNextMessage() != null && job.getMessageType().equals(msg.getMessageType())) {
                    logger.info("找到一个处理出货结果的job");
                    // 查询出货结果(要循环)
                    ShipmentResultMessage shipmentResultMessage = (ShipmentResultMessage)msg;

                    if (shipmentResultMessage.getStatusCode() == 3||shipmentResultMessage.getStatusCode() == 4||shipmentResultMessage.getStatusCode() == 8) {
                        // 出货终止或超时
                        mqttSender.sendShipmentResult(shipmentResultMessage, job.getTheme());
                        jobs.remove(job);
                    }else if (shipmentResultMessage.getStatusCode() == 0 || shipmentResultMessage.getStatusCode() == 1) {
                        // 货道板串口没有返回数据或出货指令没有执行。超过10次则判断是出货失败，直接返回上层
                        if (shipmentResultMessage.getStatusCode() == 0) {
                            job.setTimes(job.getTimes()+1);
                        }
                        if (job.getTimes() > 9) {
                            mqttSender.sendShipmentResult(shipmentResultMessage, job.getTheme());
                            jobs.remove(job);
                        }else {
                            try {
                                Thread.sleep(2000);
                                nettyMessageHandler.channelWrite(NettyMessageHandler.clientList.get(machineNo), job.getNextMessage());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }else if (shipmentResultMessage.getStatusCode() == 2) {
                        // 出货完成(货道空闲)
                        mqttSender.sendShipmentResult(shipmentResultMessage, job.getTheme());
                        jobs.remove(job);
                    }
                    break;
                }
            }

            return;

        }

    }

}