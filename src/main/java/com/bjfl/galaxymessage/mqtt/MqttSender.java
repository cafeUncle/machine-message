package com.bjfl.galaxymessage.mqtt;

import com.bjfl.galaxymessage.messages.CellStatusMessage;
import com.bjfl.galaxymessage.messages.ShipmentResultMessage;
import org.springframework.stereotype.Controller;

@Controller
public class MqttSender {

    public void sendShipmentResult(ShipmentResultMessage shipmentResultMessage) {
        // extract message machineCode orderCode
        shipmentResultMessage.getOrderCode();
        shipmentResultMessage.getMachineCode(13);
        //
    }

    public void sendCellStatus(CellStatusMessage msg) {

    }
}
