package com.bjfl.galaxymessage.parser.codeParser;

public class ShipmentStatusParser {

    public static String parse(int code) {
        switch (code) {
            case 1005:
                return "接到出货命令";
            case 1010:
                return "电机上升启动";
            case 1015:
                return "电机上升完成";
            case 1020:
                return "开始掉货";
            case 1025:
                return "掉货成功";
            case 1030:
                return "寻找出货口";
            case 1035:
                return "到达出货口";
            case 1040:
                return "出货成功";
            case 1045:
                return "等待取货";
            case 1050:
                return "货物已被取走";
            case 1055:
                return "开始关门";
            case 1060:
                return "关门成功";
            case 1065:
                return "货道空闲";
            case 1070:
                return "货道复位";
            case 1075:
                return "货道忙碌";
            case 1080:
                return "货道故障";
            default:
                return "未知出货状态";
        }
    }
}
