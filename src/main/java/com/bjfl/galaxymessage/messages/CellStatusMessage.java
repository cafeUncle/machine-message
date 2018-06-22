package com.bjfl.galaxymessage.messages;

import com.bjfl.galaxymessage.parser.MessageType;
import com.bjfl.galaxymessage.util.Constants;
import io.netty.channel.ChannelHandlerContext;

import java.util.Arrays;

public class CellStatusMessage extends Message {

    public CellStatusMessage() {
        super();
    }

    public CellStatusMessage(int[] ints) {
        super(ints);
    }

    public int getShipmentProcessCode() {
        return this.ints[20]*256 + this.ints[21];
    }

    public int getShipmentStatusCode() {
        return this.ints[22]*256 + this.ints[23];
    }

    public int getCellStatusCode() {
        return this.ints[24]*256 + this.ints[25];
    }

    public int getStatusCode(){
        return ints[26];
    }

    @Override
    public void print(ChannelHandlerContext ctx) {
        super.print(ctx);

        String machineCode = getMachineCode(Constants.NORMAL_MESSAGE_MACHINE_CODE_OFFSET);

        logger.info("查询货道状态rec:" + machineCode +
                ", 能否出货(0能1不能):" + getStatusCode() +
                ", 16进制数组:" + Arrays.toString(this.ints));

    }
}
