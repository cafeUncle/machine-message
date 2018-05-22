package com.bjfl.galaxymessage.parser;

import com.bjfl.galaxymessage.messages.CellStatusMessage;
import com.bjfl.galaxymessage.messages.Message;
import com.bjfl.galaxymessage.messages.ShipmentMessage;
import com.bjfl.galaxymessage.util.MessageUtil;

import java.util.List;

public class CellStatusMessageParser {

    /**
     * 该类生成所有执行前需先查货道状态进行握手的指令
     * @param cellStatusMessage
     * @return
     */
    public static Message parse(CellStatusMessage cellStatusMessage) {

        switch (cellStatusMessage.getNextCommand()) {
            case SHIPMENT:
//                String machineCode = cellStatusMessage.getMachineCode(6);
//                cellStatusMessage.getCabinetAddress();
//                cellStatusMessage.getF();
//                cellStatusMessage.getB();
//                cellStatusMessage.getLr();
//                cellStatusMessage.getOrderCode();
//                List<Integer> integers = MessageUtil.strTo16(machineCode);
//                integers.add()
//                integers.add()
//                integers.add()
//                ShipmentMessage shipmentMessage = new ShipmentMessage();
//                shipmentMessage.generate(machineCode, cabinetAddress, MessageType.SHIPMENT.getCode(), integers);
//                return shipmentMessage;
            case SHIPMENT_LOG:
            case RESET:
            case PREPOSE_MOTOR_CASE:
            case PREPOSE_MOTOR_HOME:
            case COORDINATE_CASE:
            case COORDINATE_HOME:
            default:
                return null;
        }
    }
}
