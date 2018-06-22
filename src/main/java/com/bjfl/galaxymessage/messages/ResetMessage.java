package com.bjfl.galaxymessage.messages;

import com.bjfl.galaxymessage.util.Constants;
import io.netty.channel.ChannelHandlerContext;

public class ResetMessage extends Message {

    public ResetMessage() {
        super();
    }

    public ResetMessage(int[] ints) {
        super(ints);
    }

    @Override
    public void print(ChannelHandlerContext ctx) {
        super.print(ctx);

        String machineCode = getMachineCode(Constants.NORMAL_MESSAGE_MACHINE_CODE_OFFSET);

        logger.info("复位系统rec:" + machineCode + ", 回复状态(1货道主板复位成功 2通讯主板复位成功):" + this.ints[20]);
    }
}
