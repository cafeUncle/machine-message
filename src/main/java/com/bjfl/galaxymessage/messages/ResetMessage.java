package com.bjfl.galaxymessage.messages;

import com.bjfl.galaxymessage.netty.NettyMessageHandler;
import io.netty.channel.ChannelHandlerContext;

public class ResetMessage extends Message {

    public ResetMessage(){}

    public ResetMessage(int[] ints) {
        super(ints);
    }

    @Override
    public void deal(ChannelHandlerContext ctx) {
        super.deal(ctx);

        String machineCode = getMachineCode(6);

        System.out.println("复位系统rec:" + machineCode + ", 回复状态(1货道主板复位成功 2通讯主板复位成功):" + this.ints[20]);
    }
}
