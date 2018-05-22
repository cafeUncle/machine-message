package com.bjfl.galaxymessage.messages;

import io.netty.channel.ChannelHandlerContext;

import java.util.Arrays;

public class CoorDinateCaseMessage extends Message {

    public CoorDinateCaseMessage() {
        super();
    }

    public CoorDinateCaseMessage(int[] ints) {
        super(ints);
    }

    @Override
    public void deal(ChannelHandlerContext ctx) {
        super.deal(ctx);

        String machineCode = getMachineCode();

        System.out.println("查询货道状态rec:" + machineCode +
                ", 能否出货(0能1不能):" + this.ints[26] +
                ", 16进制数组:" + Arrays.toString(this.ints));

    }
}
