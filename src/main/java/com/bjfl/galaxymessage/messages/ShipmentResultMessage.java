package com.bjfl.galaxymessage.messages;

import io.netty.channel.ChannelHandlerContext;

public class ShipmentResultMessage extends Message {

    public ShipmentResultMessage(){}

    public ShipmentResultMessage(int[] ints) {
        super(ints);
    }

    public String getOrderCode() {
        return new String(this.ints, 8, 25);
    }

    @Override
    public void deal(ChannelHandlerContext ctx) {
        super.deal(ctx);

        String machineCode = getMachineCode(6);

        System.out.println("查询出货结果rec:" + machineCode
//                + ", \n状态(0货道板串口没有返回数据或出货指令没有执行,长时间没有返回时需要向报错如网络异常1正在出货2出货完成3出货终止(发生复位，需获取当前日志)4出货终止(货道异常，需获取当前日志)5出货超时(发生超时，需获取当前日志):" + this.ints[this.ints.length-6]
//                + ", 是否扣钱(0不扣钱1扣钱):" + this.ints[this.ints.length-5]
//                + ", 当前出货处理结果(1未处理0处理完成):" + this.ints[this.ints.length-4]);
                + ", 状态:" + parseStatus(this.ints[this.ints.length-6])
                + ", 是否扣钱:" + parsePay(this.ints[this.ints.length-5])
                + ", 当前出货处理结果:" + parseResult(this.ints[this.ints.length-4])
                + ", 订单号:" + this.getOrderCode());

    }

    private String parseStatus(int code) {
        switch (code) {
            case 0: return "货道板串口没有返回数据或出货指令没有执行,长时间没有返回时需要向报错如网络异常";
            case 1: return "正在出货";
            case 2: return "出货完成";
            case 3: return "出货终止(发生复位，需获取当前日志)";
            case 4: return "出货终止(货道异常，需获取当前日志)";
            case 5: return "出货超时(发生超时，需获取当前日志)";
            default:return "未知状态码";
        }
    }

    private String parsePay(int code) {
        switch (code) {
            case 0: return "不扣钱";
            case 1: return "扣钱";
            default:return "未知状态码";
        }
    }

    private String parseResult(int code) {
        switch (code) {
            case 1: return "未处理";
            case 0: return "处理完成";
            default:return "未知状态码";
        }
    }
}
