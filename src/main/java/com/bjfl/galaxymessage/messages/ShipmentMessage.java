package com.bjfl.galaxymessage.messages;

import com.bjfl.galaxymessage.util.Constants;
import com.bjfl.galaxymessage.util.MessageUtil;
import io.netty.channel.ChannelHandlerContext;

public class ShipmentMessage extends Message {

    public ShipmentMessage(){
        super();
    }

    public ShipmentMessage(int[] ints) {
        super(ints);
    }

    @Override
    public void deal(ChannelHandlerContext ctx) {
        super.deal(ctx);

        String machineCode = getMachineCode(Constants.NORMAL_MESSAGE_MACHINE_CODE_OFFSET);

        logger.info("整体出货指令rec:" + machineCode
                + ", 是否执行成功(1成功2失败):" + this.ints[this.ints.length-3]
                + ",16进制数组:" + MessageUtil.intsToHexString(this.ints));

    }
}
