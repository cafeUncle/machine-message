package com.bjfl.galaxymessage.netty;

import com.bjfl.galaxymessage.jobs.JobBus;
import com.bjfl.galaxymessage.messages.*;
import com.bjfl.galaxymessage.mqtt.MqttSender;
import com.bjfl.galaxymessage.parser.MessageFactory;
import com.bjfl.galaxymessage.parser.MessageType;
import com.bjfl.galaxymessage.util.Constants;
import com.bjfl.galaxymessage.util.MessageUtil;
import com.bjfl.galaxymessage.websocket.MyWebSocket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@ChannelHandler.Sharable
public class NettyMessageHandler extends ChannelHandlerAdapter {

    final Logger logger = LoggerFactory.getLogger(NettyMessageHandler.class);

    @Autowired
    MqttSender mqttSender;
    @Autowired
    MyWebSocket myWebSocket;
    @Autowired
    JobBus jobBus;

    public static final Map<String, ChannelHandlerContext> clientList = new ConcurrentHashMap<>();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object o) {
        try {
            ByteBuf buf = (ByteBuf) o;
            byte[] byteArray = new byte[buf.readableBytes()];
            buf.readBytes(byteArray);
            logger.info(logger.getName() + "rec bytes:" + Arrays.toString(byteArray));
            int[] msgArr = new int[byteArray.length + 1];
            for (int i = 0; i < byteArray.length; i++) {
                msgArr[i] = byteArray[i] & 0xff;
            }
            msgArr[msgArr.length - 1] = 0xED;
            logger.info("rec ints:" + Arrays.toString(msgArr));

            if (MessageUtil.validate(msgArr)) {
                logger.info("validate done :" + MessageUtil.intsToHexString(msgArr));

                Message msg = MessageFactory.parse(msgArr);

                if (msg != null) {
                    msg.print(ctx);

                    MyWebSocket.sendMessageToAll(Arrays.toString(msg.getInts()));

                    if (msg instanceof HeartBeatMessage || msg instanceof RegisterMessage) {
                        String machineNo = msg.getMachineCode(Constants.HEART_BEAT_MACHINE_CODE_OFFSET);
                        if (JobBus.jobs.get(machineNo) == null){
                            JobBus.jobs.put(machineNo, new CopyOnWriteArrayList<>());
                        }
                        clientList.put(machineNo, ctx);
                    } else if (msg instanceof CellStatusMessage) {
                        CellStatusMessage cellStatusMessage = (CellStatusMessage) msg;
                        logger.info("rec cellStatusMessage:" + cellStatusMessage.toString());
                        jobBus.doNext(cellStatusMessage);
                    } else {
                        jobBus.doBack(msg);
                    }
                }
            } else {
                logger.error("validate error:" + MessageUtil.intsToHexString(msgArr));
            }
        } finally {
            ReferenceCountUtil.release(o);
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        boolean isOpen = channel.isOpen();
        if (isOpen) {
            logger.info("监听到客户端进入：[" + channel.remoteAddress() + "] is open");
        } else {
            logger.info("监听到客户端进入：[" + channel.remoteAddress() + "] is unOpen");
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        boolean isOpen = channel.isOpen();
        if (isOpen) {
            logger.info("监听到客户端退出：[" + channel.remoteAddress() + "] is open");
        } else {
            if (clientList.containsValue(ctx)) {
                for (Map.Entry<String, ChannelHandlerContext> entry : clientList.entrySet()) {
                    if (ctx.equals(entry.getValue())) {
                        clientList.remove(entry.getKey());
                        logger.info("[" + entry.getKey() + "] 退出了");
                        break;
                    }
                }
            }
            ctx.close();
            logger.info("监听到客户端退出：[" + channel.remoteAddress() + "] is unOpen");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (clientList.containsValue(ctx)) {
            for (Map.Entry<String, ChannelHandlerContext> entry : clientList.entrySet()) {
                if (ctx.equals(entry.getValue())) {
                    clientList.remove(entry.getKey());
                    logger.error("[" + entry.getKey() + "] exception:" + cause.getMessage());
                    cause.printStackTrace();
                    break;
                }
            }
        }
        ctx.close();
    }

    public void channelWrite(ChannelHandlerContext channelHandlerContext, Message msg) {
        ByteBuf heapBuffer = Unpooled.buffer(msg.getInts().length);
        heapBuffer.writeBytes(MessageUtil.intArrToByteArr(msg.getInts()));
        channelHandlerContext.channel().writeAndFlush(heapBuffer);
        logger.info("sendToMachine-" + new String(msg.getInts(), 3, 13) + ":" + MessageUtil.intsToHexString(msg.getInts()));
    }

}