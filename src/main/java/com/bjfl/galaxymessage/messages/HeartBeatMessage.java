package com.bjfl.galaxymessage.messages;

import com.bjfl.galaxymessage.netty.NettyMessageHandler;
import io.netty.channel.ChannelHandlerContext;

public class HeartBeatMessage extends Message {
    public HeartBeatMessage() {
    }

    public HeartBeatMessage(int[] ints) {
        super(ints);
    }



    @Override
    public void deal(ChannelHandlerContext ctx) {
        super.deal(ctx);

        String machineCode = getMachineCode(3);

        NettyMessageHandler.clientList.put(machineCode, ctx);

        System.out.println(machineCode);

    }
}
