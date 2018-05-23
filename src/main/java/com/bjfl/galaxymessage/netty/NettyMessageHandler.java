package com.bjfl.galaxymessage.netty;

import com.bjfl.galaxymessage.messages.*;
import com.bjfl.galaxymessage.mqtt.MqttSender;
import com.bjfl.galaxymessage.parser.CellStatusMessageParser;
import com.bjfl.galaxymessage.parser.MessageFactory;
import com.bjfl.galaxymessage.parser.MessageType;
import com.bjfl.galaxymessage.util.Constants;
import com.bjfl.galaxymessage.util.MessageUtil;
import com.bjfl.galaxymessage.websocket.MyWebSocket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ChannelHandler.Sharable
public class NettyMessageHandler extends ChannelHandlerAdapter {

    @Autowired
    MqttSender mqttSender;
    @Autowired
    MyWebSocket myWebSocket;

    public static final Map<String, ChannelHandlerContext> clientList = new ConcurrentHashMap<>();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object o) {
        try {
            ByteBuf buf = (ByteBuf) o;
            byte[] byteArray = new byte[buf.readableBytes()];
            buf.readBytes(byteArray);
            int[] msgArr = new int[byteArray.length];
            for (int i = 0; i < byteArray.length; i++) {
                msgArr[i] = byteArray[i] & 0xff;
            }

            if (MessageUtil.validate(msgArr)) {
                System.out.println("validate done :" + MessageUtil.intsToHexString(msgArr));

                Message msg = MessageFactory.parse(msgArr);

                if (msg != null) {
                    msg.deal(ctx);

                    MyWebSocket.sendMessageToAll(Arrays.toString(msg.getInts())); // todo: 测试阶段放开

                    if (msg instanceof CellStatusMessage) {

                        CellStatusMessage cellStatusMessage = (CellStatusMessage) msg;

                        if (MessageType.CELL_STATUS.equals(cellStatusMessage.getNextCommand())) {
                            mqttSender.sendCellStatus((CellStatusMessage)msg);

                        }else {
                            Message nextMessage = CellStatusMessageParser.parse(cellStatusMessage);
                            if (nextMessage != null) {
                                channelWrite(clientList.get(nextMessage.getMachineCode(Constants.NORMAL_MESSAGE_MACHINE_CODE_OFFSET)), nextMessage);
                            }else {
                                System.out.println("cellStatus next command parse error, " + MessageUtil.intsToHexString(msg.getInts()));
                            }
                        }
                    }else if (msg instanceof HeartBeatMessage) {
                        mqttSender.sendHeartBeatsMessage((HeartBeatMessage)msg);

                    }else if (msg instanceof ShipmentMessage) {
                        mqttSender.sendShipment((ShipmentMessage)msg);

                    }else if (msg instanceof ShipmentResultMessage) {
                        mqttSender.sendShipmentResult((ShipmentResultMessage)msg);

                    }else if (msg instanceof ShipmentLogMessage) {
                        mqttSender.sendShipmentLogMessage((ShipmentLogMessage)msg);

                    }
                }
            } else {
                System.out.println("validate error:" + MessageUtil.intsToHexString(msgArr));
            }
        } finally {
            ReferenceCountUtil.release(o);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        boolean active = channel.isActive();
        if (active) {
            System.out.println("[" + channel.remoteAddress() + "] is online");
        } else {
            System.out.println("[" + channel.remoteAddress() + "] is offline");
        }
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
        System.out.println(ctx.channel().remoteAddress() + ":unregister");
    }

    @Override
    public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        super.deregister(ctx, promise);
        System.out.println(ctx.channel().remoteAddress() + ":deregister");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        /**
         * exceptionCaught() 事件处理方法是当出现 Throwable 对象才会被调用，即当 Netty 由于 IO
         * 错误或者处理器在处理事件时抛出的异常时。在大部分情况下，捕获的异常应该被记录下来 并且把关联的 channel
         * 给关闭掉。然而这个方法的处理方式会在遇到不同异常的情况下有不同的实现，比如你可能想在关闭连接之前发送一个错误码的响应消息。
         */
        if (clientList.containsValue(ctx)) {
            for (Map.Entry<String, ChannelHandlerContext> entry : clientList.entrySet()) {
                if (ctx.equals(entry.getValue())) {
                    clientList.remove(entry.getKey());
                    System.out.println("[" + entry.getKey() + "] exception:" + cause.toString());
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
        System.out.println("sendToMachine-" + new String(msg.getInts(), 3, 13) + ":" + MessageUtil.intsToHexString(msg.getInts()));
    }

}