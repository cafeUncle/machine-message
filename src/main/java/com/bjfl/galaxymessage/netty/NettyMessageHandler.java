package com.bjfl.galaxymessage.netty;

import com.bjfl.galaxymessage.messages.Message;
import com.bjfl.galaxymessage.messages.ShipmentResultMessage;
import com.bjfl.galaxymessage.parser.MessageFactory;
import com.bjfl.galaxymessage.process.ShipmentProcess;
import com.bjfl.galaxymessage.util.MessageUtil;
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

/**
 * 服务端处理通道.这里只是打印一下请求的内容，并不对请求进行任何的响应 NettyMessageHandler 继承自
 * ChannelHandlerAdapter， 这个类实现了ChannelHandler接口， ChannelHandler提供了许多事件处理的接口方法，
 * 然后你可以覆盖这些方法。 现在仅仅只需要继承ChannelHandlerAdapter类而不是你自己去实现接口方法。
 */
@Component
@ChannelHandler.Sharable
public class NettyMessageHandler extends ChannelHandlerAdapter {

    @Autowired
    ShipmentProcess shipmentProcess;

    public static final ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    public static final Map<String, ChannelHandlerContext> clientList = new ConcurrentHashMap<>();

    /**
     * 这里我们覆盖了chanelRead()事件处理方法。 每当从客户端收到新的数据时， 这个方法会在收到消息时被调用，
     * 这个例子中，收到的消息的类型是ByteBuf
     *
     * @param ctx 通道处理的上下文信息
     * @param o   接收的消息
     */
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
//            System.out.println("rec int[]:" + Arrays.toString(msgArr));
//            System.out.println("rec hex:" + MessageUtil.intsToHexString(msgArr));

            if (MessageUtil.validate(msgArr)) {
                Message msg = MessageFactory.parse(msgArr);

                if (msg != null) {
                    msg.deal(ctx);

                    if (msg instanceof ShipmentResultMessage) {
                        shipmentProcess.deal(msg);
                    }
                }
            } else {
                System.out.println("校验失败");
            }
        } finally {
            /**
             * ByteBuf是一个引用计数对象，这个对象必须显示地调用release()方法来释放。
             * 请记住处理器的职责是释放所有传递到处理器的引用计数对象。
             */
            // 抛弃收到的数据
            ReferenceCountUtil.release(o);
        }
    }

    /**
     * 处理新加的消息通道
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        for (Channel ch : group) {
            if (ch == channel) {
//                ch.writeAndFlush("[" + channel.remoteAddress() + "] coming");
                System.out.println("[" + channel.remoteAddress() + "] coming");
            }
        }
        group.add(channel);
        System.out.println("新增消息通道");
    }

    /**
     * 处理退出消息通道
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        for (Channel ch : group) {
            if (ch == channel) {
//                ch.writeAndFlush("[" + channel.remoteAddress() + "] leaving");
                System.out.println("[" + channel.remoteAddress() + "] leaving");
            }
        }
        System.out.println("退出消息通道");
        group.remove(channel);
    }

    /**
     * 在建立连接时发送消息
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        boolean active = channel.isActive();
        if (active) {
            System.out.println("[" + channel.remoteAddress() + "] is online");
        } else {
            System.out.println("[" + channel.remoteAddress() + "] is offline");
        }
//        ctx.writeAndFlush("[server]: welcome");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        /**
         * exceptionCaught() 事件处理方法是当出现 Throwable 对象才会被调用，即当 Netty 由于 IO
         * 错误或者处理器在处理事件时抛出的异常时。在大部分情况下，捕获的异常应该被记录下来 并且把关联的 channel
         * 给关闭掉。然而这个方法的处理方式会在遇到不同异常的情况下有不同的实现，比如你可能想在关闭连接之前发送一个错误码的响应消息。
         */
        // 出现异常就关闭
        System.out.println("[" + ctx.channel().remoteAddress() + "] exception:" + cause.toString());
        if (clientList.containsValue(ctx)) {
            for (Map.Entry<String, ChannelHandlerContext> entry : clientList.entrySet()) {
                if (ctx.equals(entry.getValue())) {
                    clientList.remove(entry.getKey());
                    break;
                }
            }
        }
        cause.printStackTrace();
        ctx.close();
    }


    public void channelWrite(ChannelHandlerContext channelHandlerContext, Message msg) {
        ByteBuf heapBuffer = Unpooled.buffer(msg.getInts().length);
        heapBuffer.writeBytes(MessageUtil.intArrToByteArr(msg.getInts()));

        channelHandlerContext.channel().writeAndFlush(heapBuffer).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
//                    System.out.println("arrived");
                } else {
//                    System.out.println("fail");
                }
                if (future.isDone()) {
//                    System.out.println("done");
                } else {
//                    System.out.println("lose");
                }
            }
        });

//        ReferenceCountUtil.release(heapBuffer);

    }

}