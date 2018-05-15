package com.bjfl.galaxymessage.message;

public enum MessageEnum {

    RESET("复位", 0x31),
    CELL_STATUS("查询当前货道状态", 0x32),
    SHIPMENT("整体出货指令", 0x33),
    SHIPMENT_RESULT("查询当前出货结果", 0x34),
    SHIPMENT_LOG("获取出货日志", 0x4A);

    private String opt;
    private int code;

    MessageEnum(String opt, int code) {
        this.opt = opt;
        this.code = code;
    }

    public boolean equals(MessageEnum messageEnum) {
        if (this.code == (messageEnum.code)) {
            return true;
        }
        return false;
    }

}
