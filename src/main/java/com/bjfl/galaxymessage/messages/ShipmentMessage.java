package com.bjfl.galaxymessage.messages;

import io.netty.channel.ChannelHandlerContext;

public class ShipmentMessage extends Message {

    public ShipmentMessage(){}

    public ShipmentMessage(int[] ints) {
        super(ints);
    }

    @Override
    public void deal(ChannelHandlerContext ctx) {
        super.deal(ctx);

        String machineCode = getMachineCode(6);

        System.out.println("整体出货指令rec:" + machineCode + ", 是否执行成功(1成功2失败):" + this.ints[this.ints.length-3]);

    }
}
