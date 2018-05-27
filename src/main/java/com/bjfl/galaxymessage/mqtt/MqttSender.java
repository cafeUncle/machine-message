package com.bjfl.galaxymessage.mqtt;

import com.alibaba.fastjson.JSONObject;
import com.bjfl.galaxymessage.messages.*;
import com.bjfl.galaxymessage.util.Constants;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MqttSender {

    @Autowired
    MqttConfiguration mqttConfiguration;

    @Autowired
    MqttClient mqttClient;

    @Value("${heartBeats.topic}")
    String heartBeatsTopic;

    public void sendCellStatus(CellStatusMessage cellStatusMessage) {
        MqttTopic clientTopic = mqttClient.getTopic(cellStatusMessage.getTopic());
        MqttMessage message = new MqttMessage("Hello World. Hello MQTT.".getBytes());
        message.setQos(Constants.MQTT_QOS);
        message.setRetained(false);
        try {
            clientTopic.publish(message);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void sendShipment(ShipmentMessage shipmentMessage) {
//        message.setRetained(false);  重要的保留，不重要的不需要保留
    }

    public void sendShipmentResult(ShipmentResultMessage shipmentResultMessage) {
        // extract message machineCode orderCode
        shipmentResultMessage.getOrderCode();
        shipmentResultMessage.getMachineCode(Constants.NORMAL_MESSAGE_MACHINE_CODE_OFFSET);
        //
//        message.setRetained(false);
    }

    public void sendShipmentLogMessage(ShipmentLogMessage shipmentLogMessage) {

    }

    public void sendHeartBeatsMessage(HeartBeatMessage heartBeatMessage) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("machineNo", heartBeatMessage.getMachineCode(Constants.HEART_BEAT_MACHINE_CODE_OFFSET));
        this.send(jsonObject, heartBeatsTopic);
    }

    public void sendShipmentErrorMessage(HeartBeatMessage heartBeatMessage) {

    }

    private void send(JSONObject jsonObject, String topic) {
        jsonObject.put("timestamp", System.currentTimeMillis());
        jsonObject.put("expireTime", 1000);
        MqttTopic clientTopic = mqttClient.getTopic(topic);
        MqttMessage message = new MqttMessage(jsonObject.toString().getBytes());
        message.setQos(Constants.MQTT_QOS);
        message.setRetained(true);
        try {
            clientTopic.publish(message);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}
