package com.bjfl.galaxymessage.parser.codeParser;

public class ShipmentErrorParser {

    public static String parse(int code) {
        switch (code) {
            case 5:
                return "关门失败";
            case 10:
                return "开门超时";
            case 15:
                return "推货电机回收超时";
            case 20:
                return "前置电机没有到位";
            case 25:
                return "后置电机没有到位";
            case 30:
                return "左右电机没有到位";
            case 240:
                return "出货正常";
            default:
                return "未知货道状态";
        }
    }
}
