package com.bjfl.galaxymessage.messages;

import com.bjfl.galaxymessage.netty.NettyMessageHandler;
import com.bjfl.galaxymessage.util.Constants;
import io.netty.channel.ChannelHandlerContext;

public class HeartBeatMessage extends Message {
    public HeartBeatMessage() {
        super();
    }

    public HeartBeatMessage(int[] ints) {
        super(ints);
    }

    @Override
    public void deal(ChannelHandlerContext ctx) {
        super.deal(ctx);

        String machineCode = getMachineCode(Constants.HEART_BEAT_MACHINE_CODE_OFFSET);

        NettyMessageHandler.clientList.put(machineCode, ctx);

        logger.info(machineCode);

    }
}
