package com.bjfl.galaxymessage.messages;

import com.bjfl.galaxymessage.util.Constants;
import com.bjfl.galaxymessage.util.MessageUtil;
import io.netty.channel.ChannelHandlerContext;

public class ShipmentMessage extends Message {

    public ShipmentMessage() {
        super();
    }

    public ShipmentMessage(int[] ints) {
        super(ints);
    }

    public int getStatusCode() {
        return ints[ints.length - 3];
    }

    public String getOrderCode() {
        return new String(this.ints, 22, 25);
    }

    @Override
    public void print(ChannelHandlerContext ctx) {
        super.print(ctx);

        String machineCode = getMachineCode(Constants.NORMAL_MESSAGE_MACHINE_CODE_OFFSET);

        logger.info("整体出货指令rec:" + machineCode
                + ", 是否执行成功(1成功0失败):" + getStatusCode()
                + ",16进制数组:" + MessageUtil.intsToHexString(this.ints));

    }
}
