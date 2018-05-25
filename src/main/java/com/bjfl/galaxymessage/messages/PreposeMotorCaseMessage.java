package com.bjfl.galaxymessage.messages;

import com.bjfl.galaxymessage.util.Constants;
import io.netty.channel.ChannelHandlerContext;

import java.util.Arrays;

public class PreposeMotorCaseMessage extends Message {

    public PreposeMotorCaseMessage() {
        super();
    }

    public PreposeMotorCaseMessage(int[] ints) {
        super(ints);
    }

    @Override
    public void deal(ChannelHandlerContext ctx) {
        super.deal(ctx);

        String machineCode = getMachineCode(Constants.NORMAL_MESSAGE_MACHINE_CODE_OFFSET);

        System.out.println("前置电机测试rec:" + machineCode +
                ", 是否成功(1成功2失败):" + this.ints[23] +
                ", 16进制数组:" + Arrays.toString(this.ints));
    }
}
