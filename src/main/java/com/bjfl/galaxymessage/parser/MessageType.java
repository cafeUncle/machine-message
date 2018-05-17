package com.bjfl.galaxymessage.parser;

public enum MessageType {

    UNKNOWN("未知", 0x00),

    REGISTER("注册", 0x0D),
    HEART_BEAT("心跳", 0x0A),
    RESET("复位", 0x31),
    CELL_STATUS("查询当前货道状态", 0x32),
    SHIPMENT("整体出货指令", 0x33),
    SHIPMENT_RESULT("查询当前出货结果", 0x34),
    SHIPMENT_LOG("获取出货日志", 0x4A);

    private String opt;
    private int code;

    MessageType(String opt, int code) {
        this.opt = opt;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static MessageType getEnum(int type) {
        MessageType[] values = MessageType.values();
        for (int i = 0; i < values.length; i++) {
            if (values[i].code == type) {
                return values[i];
            }
        }
        return UNKNOWN;
    }
}
