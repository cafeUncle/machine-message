package com.bjfl.galaxymessage.message;

import com.bjfl.galaxymessage.receiver.*;

public class MessageFactory {

    public static Message parse(int[] msg){
        if (msg.length == 20) {
            if (msg[16] == 0x0D) {
                return new RegisterMessage(msg);
            }else if (msg[16] == 0x0A) {
                return new HeartBeatMessage(msg);
            }
        }

        MessageType messageType = MessageType.getEnum(msg[4] - 0x80);

        switch (messageType) {
            case REGISTER:
                return new RegisterMessage(msg);
            case HEART_BEAT:
                return new HeartBeatMessage(msg);
            case RESET:
                return new ResetMessage(msg);
            case CELL_STATUS:
                return new CellStatusMessage(msg);
            case SHIPMENT:
                return new Shipment(msg);
            case SHIPMENT_RESULT:
                return new ShipmentResultMessage(msg);
            case SHIPMENT_LOG:
                return new ShipmentLog(msg);
            default:
                return null;
        }
    }
}
