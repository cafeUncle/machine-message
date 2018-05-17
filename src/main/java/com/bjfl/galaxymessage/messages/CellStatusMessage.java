package com.bjfl.galaxymessage.messages;

import io.netty.channel.ChannelHandlerContext;

public class CellStatusMessage extends Message {

    public CellStatusMessage() {
    }

    public CellStatusMessage(int[] ints) {
        super(ints);
    }

    @Override
    public void deal(ChannelHandlerContext ctx) {
        super.deal(ctx);

        String machineCode = getMachineCode(6);

        System.out.println("查询货道状态rec:" + machineCode + ", 能否出货(0能1不能):" + this.ints[26]);

    }
}
