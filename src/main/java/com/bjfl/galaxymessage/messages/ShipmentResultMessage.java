package com.bjfl.galaxymessage.messages;

import com.bjfl.galaxymessage.util.Constants;
import com.bjfl.galaxymessage.util.MessageUtil;
import io.netty.channel.ChannelHandlerContext;

public class ShipmentResultMessage extends Message {

    public ShipmentResultMessage() {
        super();
    }

    public ShipmentResultMessage(int[] ints) {
        super(ints);
    }

    public String getOrderCode() {
        return new String(this.ints, 8, 25);
    }

    public int getShipmentProcessCode() {
        return this.ints[45]*256 + this.ints[46];
    }

    public int getShipmentStatusCode() {
        return this.ints[47]*256 + this.ints[48];
    }

    public int getCellStatusCode() {
        return this.ints[49]*256 + this.ints[50];
    }

    public int getStatusCode() {
        return this.ints[this.ints.length - 6];
    }

    public int getPayCode() {
        return this.ints[this.ints.length - 5];
    }

    public int getResultCode() {
        return this.ints[this.ints.length - 4];
    }

    @Override
    public void print(ChannelHandlerContext ctx) {
        super.print(ctx);

        logger.info("查询出货结果rec:" + this.getMachineCode(Constants.NORMAL_MESSAGE_MACHINE_CODE_OFFSET)
                + ", 状态:" + this.parseStatus()
                + ", 是否扣钱:" + this.parsePay()
                + ", 当前出货处理结果:" + this.parseResult()
                + ", 订单号:" + this.getOrderCode()
                + ", 16进制数组:" + MessageUtil.intsToHexString(this.ints));
    }

    public String parseStatus() {
        switch (getStatusCode()) {
            case 0:
                return "货道板串口没有返回数据或出货指令没有执行,长时间没有返回时需要向报错如网络异常";
            case 1:
                return "正在出货";
            case 2:
                return "出货完成";
            case 3:
                return "出货终止(发生复位，需获取当前日志)";
            case 4:
                return "出货终止(货道异常，需获取当前日志)";
            case 5:
                return "出货超时(发生超时，需获取当前日志)";
            default:
                return "未知状态码";
        }
    }

    public String parsePay() {
        switch (getPayCode()) {
            case 0:
                return "不扣钱";
            case 1:
                return "扣钱";
            default:
                return "未知状态码";
        }
    }

    public String parseResult() {
        switch (getResultCode()) {
            case 1:
                return "未处理";
            case 0:
                return "处理完成";
            default:
                return "未知状态码";
        }
    }
}
