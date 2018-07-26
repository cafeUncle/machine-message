package com.bjfl.galaxymessage.netty;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ChannelHandler.Sharable
public class NettyChannelInboundHandlerAdapter extends SimpleChannelInboundHandler {

    //netty AttributeKey 相对于 web session【重要】
    public static final AttributeKey<Map<String, String>> event = AttributeKey.valueOf("event");

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        Map<String, String> stringStringMap = channelHandlerContext.channel().attr(event).get();
        System.out.println(stringStringMap.get("mark"));
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        Map<String, String> attributeKeys = ctx.channel().attr(event).get();
        System.out.println(attributeKeys);
        if (attributeKeys == null) {
            attributeKeys = new HashMap<>();
            ctx.channel().attr(event).set(attributeKeys);
            attributeKeys.put("mark", "测试session" + ctx.channel().id());
        }else {
            String mark = attributeKeys.get("mark");
            System.out.println("reg:" + mark);
        }
    }
}
