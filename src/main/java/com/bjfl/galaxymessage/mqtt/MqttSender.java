package com.bjfl.galaxymessage.mqtt;

import com.bjfl.galaxymessage.messages.CellStatusMessage;
import com.bjfl.galaxymessage.messages.ResetMessage;
import com.bjfl.galaxymessage.messages.ShipmentMessage;
import com.bjfl.galaxymessage.messages.ShipmentResultMessage;
import com.bjfl.galaxymessage.mqtt.MqttConfiguration;
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

    public void sendShipmentResult(ShipmentResultMessage shipmentResultMessage) {
        // extract message machineCode orderCode
        shipmentResultMessage.getOrderCode();
        shipmentResultMessage.getMachineCode();
        //
    }

    public void sendCellStatus(CellStatusMessage msg) {
        MqttTopic clientTopic = mqttClient.getTopic(msg.getTopic());
        MqttMessage message = new MqttMessage("Hello World. Hello MQTT.".getBytes());
        message.setQos(1);
        try {
            clientTopic.publish("Hello World. Hello MQTT.".getBytes(), 1 ,false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void sendShipment(ShipmentMessage msg) {

    }

    public void sendResetMessage(ResetMessage msg) {

    }
}
