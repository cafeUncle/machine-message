package com.bjfl.galaxymessage.messages;

import com.bjfl.galaxymessage.netty.NettyMessageHandler;
import com.bjfl.galaxymessage.util.Constants;
import io.netty.channel.ChannelHandlerContext;

public class HeartBeatMessage extends Message {
    public HeartBeatMessage() {
        super();
        machineCodeLimit = Constants.HEART_BEAT_AND_REGISTER_MESSAGE_MACHINE_CODE_OFFSET;
    }

    public HeartBeatMessage(int[] ints) {
        super(ints);
        machineCodeLimit = Constants.HEART_BEAT_AND_REGISTER_MESSAGE_MACHINE_CODE_OFFSET;
    }

    @Override
    public void deal(ChannelHandlerContext ctx) {
        super.deal(ctx);

        String machineCode = getMachineCode();

        NettyMessageHandler.clientList.put(machineCode, ctx);

        System.out.println(machineCode);

    }
}
