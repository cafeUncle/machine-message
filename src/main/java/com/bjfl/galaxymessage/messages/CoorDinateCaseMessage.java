package com.bjfl.galaxymessage.messages;

import com.bjfl.galaxymessage.util.Constants;
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
    public void print(ChannelHandlerContext ctx) {
        super.print(ctx);

        String machineCode = getMachineCode(Constants.NORMAL_MESSAGE_MACHINE_CODE_OFFSET);

        logger.info("后置和左右电机测试rec:" + machineCode +
                ", 是否成功(1成功2失败):" + this.ints[26] +
                ", 16进制数组:" + Arrays.toString(this.ints));

    }
}
