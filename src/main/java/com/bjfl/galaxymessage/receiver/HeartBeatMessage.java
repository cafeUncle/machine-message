package com.bjfl.galaxymessage.receiver;

import com.bjfl.galaxymessage.handlers.ServerHandler;
import com.bjfl.galaxymessage.message.Message;
import io.netty.channel.ChannelHandlerContext;

public class HeartBeatMessage extends Message {
    public HeartBeatMessage() {
    }

    public HeartBeatMessage(int[] ints) {
        super(ints);
    }

    public String getMachineCode() {
        return new String(this.ints, 3, 13);
    }

    @Override
    public void deal(ChannelHandlerContext ctx) {
        super.deal(ctx);

        String machineCode = getMachineCode();

        System.out.println(machineCode);


        ServerHandler.clientList.put(machineCode, ctx);

    }
}
