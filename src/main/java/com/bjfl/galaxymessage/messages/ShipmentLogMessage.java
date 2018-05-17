package com.bjfl.galaxymessage.messages;

import io.netty.channel.ChannelHandlerContext;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ShipmentLogMessage extends Message {

    public ShipmentLogMessage(){}

    public ShipmentLogMessage(int[] ints) {
        super(ints);
    }

    @Override
    public void deal(ChannelHandlerContext ctx) {
        super.deal(ctx);

        String machineCode = getMachineCode(6);

        System.out.println("获取出货日志rec:" + machineCode
                + ", 日志(1存储芯片通讯异常2读取指令参数错误3日志读取成功4日志读取失败(没有查到当前日志)):" + this.ints[8]
                + IntStream.range(8, this.ints.length -3)
                .mapToObj((i) -> String.valueOf(this.ints[i]))
                .collect(Collectors.joining(",")));

    }
}
