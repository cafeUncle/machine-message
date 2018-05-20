package com.bjfl.galaxymessage.messages;

import com.bjfl.galaxymessage.parser.MessageType;
import io.netty.channel.ChannelHandlerContext;

import java.util.Arrays;

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

        System.out.println("查询货道状态rec:" + machineCode +
                ", 能否出货(0能1不能):" + this.ints[26] +
                ", 16进制数组:" + Arrays.toString(this.ints));

    }

    public MessageType getNextCommand() {
        return MessageType.getEnum(this.ints[28] * 256 + this.ints[29]);
    }
}
