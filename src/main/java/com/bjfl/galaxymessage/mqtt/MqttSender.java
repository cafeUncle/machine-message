package com.bjfl.galaxymessage.mqtt;

import com.bjfl.galaxymessage.messages.*;
import com.bjfl.galaxymessage.mqtt.MqttConfiguration;
import com.bjfl.galaxymessage.util.Constants;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Service
public class MqttSender {

    @Autowired
    MqttClient mqttClient;

    @Value("heartBeatsInBound.topic")
    String heartBeatsTopic;

    public void sendCellStatus(CellStatusMessage cellStatusMessage) {
        MqttTopic clientTopic = mqttClient.getTopic(cellStatusMessage.getTopic());
        MqttMessage message = new MqttMessage("Hello World. Hello MQTT.".getBytes());
        message.setQos(1);
        message.setRetained(false);
        try {
            clientTopic.publish(message);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void sendShipment(ShipmentMessage shipmentMessage) {

    }

    public void sendShipmentResult(ShipmentResultMessage shipmentResultMessage) {
        // extract message machineCode orderCode
        shipmentResultMessage.getOrderCode();
        shipmentResultMessage.getMachineCode(Constants.NORMAL_MESSAGE_MACHINE_CODE_OFFSET);
        //
    }

    public void sendShipmentLogMessage(ShipmentLogMessage shipmentLogMessage) {

    }

    public void sendResetMessage(ResetMessage resetMessage) {

    }

    public void sendPreposeMotorCaseMessage(PreposeMotorCaseMessage preposeMotorCaseMessage) {

    }

    public void sendPreposeMotorHomeMessage(PreposeMotorHomeMessage preposeMotorHomeMessage) {

    }

    public void sendCoorDinateCaseMessage(CoorDinateCaseMessage coorDinateCaseMessage) {

    }

    public void sendCoorDinateHomeMessage(CoorDinateHomeMessage coorDinateHomeMessage) {

    }
}
