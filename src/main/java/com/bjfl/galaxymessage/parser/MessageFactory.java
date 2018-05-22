package com.bjfl.galaxymessage.parser;

import com.bjfl.galaxymessage.messages.Message;
import com.bjfl.galaxymessage.messages.*;

public class MessageFactory {

    public static Message parse(int[] msg){
        if (msg.length == 20) {
            if (msg[16] == MessageType.REGISTER.getCode()) {
                return new RegisterMessage(msg);
            }else if (msg[16] == MessageType.HEART_BEAT.getCode()) {
                return new HeartBeatMessage(msg);
            }
        }

        MessageType messageType = MessageType.getEnum(msg[4] - 0x80);

        switch (messageType) {
            case CELL_STATUS:
                return new CellStatusMessage(msg);
            case SHIPMENT:
                return new ShipmentMessage(msg);
            case SHIPMENT_RESULT:
                return new ShipmentResultMessage(msg);
            case SHIPMENT_LOG:
                return new ShipmentLogMessage(msg);
            case RESET:
                return new ResetMessage(msg);
            case PREPOSE_MOTOR_CASE:
                return new PreposeMotorCaseMessage(msg);
            case PREPOSE_MOTOR_HOME:
                return new PreposeMotorHomeMessage(msg);
            case COORDINATE_CASE:
                return new CoorDinateCaseMessage(msg);
            case COORDINATE_HOME:
                return new CoorDinateHomeMessage(msg);
            default:
                return null;
        }
    }
}
