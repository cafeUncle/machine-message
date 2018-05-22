package com.bjfl.galaxymessage.parser.codeParser;

public class CellErrorParser {

    public static String parse(int code) {
        switch (code) {
            case 5:
                return "正在做前置电机测试";
            case 10:
                return "正在做后置和左右电机测试";
            case 15:
                return "系统正在初始化（等待1S左右）";
            case 20:
                return "电动门异常开启";
            case 25:
                return "电动门电流保护";
            case 30:
                return "前置电机动作时没有电压";
            case 35:
                return "后置电机动作时没有电压";
            case 40:
                return "左右电机动作时没有电压";
            case 45:
                return "推货电机动作时没有电压";
            case 50:
                return "前置电机限流保护";
            case 55:
                return "后置电机限流保护";
            case 60:
                return "左右电机限流保护";
            case 65:
                return "推货电机限流保护";
            case 70:
                return "前置电机脉冲保护";
            case 75:
                return "后置电机脉冲保护";
            case 80:
                return "左右电机脉冲保护";
            case 85:
                return "推货电机脉冲保护";
            case 90:
                return "前置电机脉冲数超限";
            case 95:
                return "后置电机脉冲数超限";
            case 100:
                return "左右电机脉冲数超限";
            case 105:
                return "前置电机上限位被压住";
            case 110:
                return "后置电机上限位被压住";
            case 115:
                return "左右电机左限位被压住";
            case 120:
                return "升降门红外传感器被遮挡";
            case 125:
                return "货箱边红外传感器被遮挡";
            case 130:
                return "出货箱红外传感器被遮挡";
            case 135:
                return "出货口门限位没有被按下";
            case 140:
                return "推货电机收限位未被按下";
            case 145:
                return "前置电机下限位未被按下";
            case 150:
                return "后置电机下限位未被按下";
            case 155:
                return "左右电机右限位未被按下";
            case 240:
                return "货道正常";
            default:
                return "未知货道状态";
        }
    }
}
