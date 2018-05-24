package com.bjfl.galaxymessage.mqtt;

import com.alibaba.fastjson.JSON;
import com.bjfl.galaxymessage.messages.*;
import com.bjfl.galaxymessage.models.request.*;
import com.bjfl.galaxymessage.netty.NettyMessageHandler;
import com.bjfl.galaxymessage.parser.MessageType;
import com.bjfl.galaxymessage.util.MessageUtil;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class MqttReceiver {

    @Autowired
    NettyMessageHandler nettyMessageHandler;

    /**
     * 测试通信服务mq连接
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                System.out.println("testInputChannel:" + message.getPayload());
            }
        };
    }

    /**
     * 查询货道状态
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = "cellStatusInputChannel")
    public MessageHandler cellStatusHandler() {
        return new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                System.out.println("cellStatusInputChannel:" + message.getPayload());
            }

        };
    }

    /**
     * 整体出货
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = "shipmentInputChannel")
    public MessageHandler shipmentHandler() {
        return new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                System.out.println("shipmentInputChannel:" + message.getPayload());
            }

        };
    }

    /**
     * 获取出货结果
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = "shipmentResultInputChannel")
    public MessageHandler shipmentResultHandler() {
        return new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                System.out.println("shipmentResultInputChannel:" + message.getPayload());
            }
        };
    }

    /**
     * 获取出货日志
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = "shipmentLogInputChannel")
    public MessageHandler shipmentLogHandler() {
        return new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                System.out.println("shipmentLogInputChannel:" + message.getPayload());
            }
        };
    }

    /**
     * 前置货梯测试
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = "preposeMotorCaseInputChannel")
    public MessageHandler preposeMotorCaseHandler() {
        return message -> {
            System.out.println("preposeMotorCaseInputChannel:" + message.getPayload().toString());
            try{
                FourthPreposeMotorCaseRequest fourthPreposeMotorCaseRequest = JSON.parseObject(message.getPayload().toString(), FourthPreposeMotorCaseRequest.class);
                if (fourthPreposeMotorCaseRequest.notExpired()){
                    String machineCode = fourthPreposeMotorCaseRequest.getMachineCode();
                    ChannelHandlerContext ctx = NettyMessageHandler.clientList.get(machineCode);
                    if (ctx != null && ctx.channel().isWritable() && ctx.channel().isActive() && ctx.channel().isOpen()) {
                        int position = fourthPreposeMotorCaseRequest.getPosition();
                        if (position == 0) {position=1;}
                        if (!sendCellStatus(machineCode, position)) {
                            return;
                        }

                        int i = fourthPreposeMotorCaseRequest.getzAxis();
                        PreposeMotorCaseMessage preposeMotorCaseMessage = new PreposeMotorCaseMessage();
                        preposeMotorCaseMessage.generate(machineCode, position, MessageType.PREPOSE_MOTOR_CASE.getCode(), Arrays.asList(0x00, 0x01, i >>> 8, i & 0xff));
                        nettyMessageHandler.channelWrite(ctx, preposeMotorCaseMessage);
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    /**
     * 前置货梯归位
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = "preposeMotorHomeInputChannel")
    public MessageHandler preposeMotorHomeHandler() {
        return message -> {
            System.out.println("preposeMotorHomeInputChannel:" + message.getPayload().toString());
            try{
                FourthPreposeMotorHomeRequest homeRequest = JSON.parseObject(message.getPayload().toString(), FourthPreposeMotorHomeRequest.class);
                if (homeRequest.notExpired()){
                    String machineCode = homeRequest.getMachineCode();
                    ChannelHandlerContext ctx = NettyMessageHandler.clientList.get(machineCode);
                    if (ctx != null && ctx.channel().isWritable() && ctx.channel().isActive() && ctx.channel().isOpen()) {
                        int position = homeRequest.getPosition();
                        if (position == 0) {position=1;}
                        if (!sendCellStatus(machineCode, position)) {
                            return;
                        }

                        PreposeMotorHomeMessage homeMessage = new PreposeMotorHomeMessage();
                        homeMessage.generate(machineCode, position, MessageType.PREPOSE_MOTOR_HOME.getCode(), Arrays.asList(0x00, 0x01));
                        nettyMessageHandler.channelWrite(ctx, homeMessage);
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    /**
     * 后置推手测试
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = "coorDinateCaseGoodInputChannel")
    public MessageHandler coorDinateCaseGoodHandler() {
        return message -> {
            System.out.println("coorDinateCaseGoodInputChannel:" + message.getPayload().toString());
            try{
                FourthCoorDinateCaseGoodRequest coordinateRequest = JSON.parseObject(message.getPayload().toString(), FourthCoorDinateCaseGoodRequest.class);
                if (coordinateRequest.notExpired()){
                    String machineCode = coordinateRequest.getMachineCode();
                    ChannelHandlerContext ctx = NettyMessageHandler.clientList.get(machineCode);
                    if (ctx != null && ctx.channel().isWritable() && ctx.channel().isActive() && ctx.channel().isOpen()) {
                        int position = coordinateRequest.getPosition();

                        if (!sendCellStatus(machineCode, position)) {
                            return;
                        }

                        int x = coordinateRequest.getxAxis();
                        int y = coordinateRequest.getyAxis();
                        CoorDinateCaseMessage caseMessage = new CoorDinateCaseMessage();
                        caseMessage.generate(machineCode, position, MessageType.COORDINATE_CASE.getCode(), Arrays.asList(0x00, 0x02, y >>> 8, y & 0xff, 0x03, x >>> 8, x & 0xff));
                        nettyMessageHandler.channelWrite(ctx, caseMessage);
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    /**
     * 后置推手归位
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = "coorDinateHomeInputChannel")
    public MessageHandler coorDinateHomeHandler() {
        return message -> {
            System.out.println("coorDinateHomeInputChannel:" + message.getPayload());
            try{
                FourthCoorDinateHomeRequest homeRequest = JSON.parseObject(message.getPayload().toString(), FourthCoorDinateHomeRequest.class);
                if (homeRequest.notExpired()){
                    String machineCode = homeRequest.getMachineCode();
                    ChannelHandlerContext ctx = NettyMessageHandler.clientList.get(machineCode);
                    if (ctx != null && ctx.channel().isWritable() && ctx.channel().isActive() && ctx.channel().isOpen()) {
                        int position = homeRequest.getPosition();

                        if (!sendCellStatus(machineCode, position)) {
                            return;
                        }

                        CoorDinateHomeMessage homeMessage = new CoorDinateHomeMessage();
                        homeMessage.generate(machineCode, position, MessageType.COORDINATE_HOME.getCode(), Arrays.asList(0x00, 0x02, 0x03));
                        nettyMessageHandler.channelWrite(ctx, homeMessage);
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    /**
     * 出货测试
     */
    @Bean
    @ServiceActivator(inputChannel = "goodsSellCaseInputChannel")
    public MessageHandler goodsSellCaseHandler() {
        return message -> {
            System.out.println("goodsSellCaseInputChannel:" + message.getPayload());
            try{
                FourthGoodsSellCaseRequest sellCaseRequest = JSON.parseObject(message.getPayload().toString(), FourthGoodsSellCaseRequest.class);
                if (sellCaseRequest.notExpired()){
                    String machineCode = sellCaseRequest.getMachineCode();
                    ChannelHandlerContext ctx = NettyMessageHandler.clientList.get(machineCode);
                    if (ctx != null && ctx.channel().isWritable() && ctx.channel().isActive() && ctx.channel().isOpen()) {
                        int position = sellCaseRequest.getPosition();

                        if (!sendCellStatus(machineCode, position)) {
                            return;
                        }

                        int x = sellCaseRequest.getxAxis();
                        int y = sellCaseRequest.getyAxis();
                        int z = sellCaseRequest.getzAxis();
                        ShipmentMessage shipmentMessage = new ShipmentMessage();
                        List<Integer> orderCodeList = MessageUtil.generateEmptyCode(25);
                        orderCodeList.add(0, 0x00);
                        orderCodeList.addAll(Arrays.asList(0x01, z >>> 8, z & 0xff));
                        orderCodeList.addAll(Arrays.asList(0x02, y >>> 8, y & 0xff));
                        orderCodeList.addAll(Arrays.asList(0x03, x >>> 8, x & 0xff));
                        shipmentMessage.generate(machineCode, position, MessageType.SHIPMENT.getCode(), orderCodeList);
                        nettyMessageHandler.channelWrite(ctx, shipmentMessage);
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    /**
     * 复位
     */
    @Bean
    @ServiceActivator(inputChannel = "goodsResetInputChannel")
    public MessageHandler goodsResetHandler() {
        return message -> {
            System.out.println("goodsResetInputChannel:" + message.getPayload());
            try{
                FourthGoodsResetRequest resetRequest = JSON.parseObject(message.getPayload().toString(), FourthGoodsResetRequest.class);
                if (resetRequest.notExpired()){
                    String machineCode = resetRequest.getMachineCode();
                    ChannelHandlerContext ctx = NettyMessageHandler.clientList.get(machineCode);
                    if (ctx != null && ctx.channel().isWritable() && ctx.channel().isActive() && ctx.channel().isOpen()) {
                        int position = resetRequest.getPosition();

                        if (!sendCellStatus(machineCode, position)) {
                            return;
                        }

                        ResetMessage resetMessage = new ResetMessage();
                        resetMessage.generate(machineCode, position, MessageType.RESET.getCode(), Arrays.asList(0x00, 0x01, 0x00));
                        nettyMessageHandler.channelWrite(ctx, resetMessage);
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    private boolean sendCellStatus(String machineCode, int position) {
        ChannelHandlerContext ctx = NettyMessageHandler.clientList.get(machineCode);
        if (ctx != null && ctx.channel().isWritable() && ctx.channel().isActive() && ctx.channel().isOpen()) {
            CellStatusMessage cellStatusMessage = new CellStatusMessage();
            cellStatusMessage.generate(machineCode, position, MessageType.CELL_STATUS.getCode(), Arrays.asList(0x00, 0x01, 0x01));
            nettyMessageHandler.channelWrite(ctx, cellStatusMessage);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }else {
            return false;
        }
    }

}
