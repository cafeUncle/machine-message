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

    @Value("${fourthShipmentError.topic}")
    String fourthShipmentErrorTopic;

    public void sendCellStatus(CellStatusMessage cellStatusMessage, String theme) {
        JSONObject result = new JSONObject();
        result.put("machineNo", cellStatusMessage.getMachineCode(Constants.NORMAL_MESSAGE_MACHINE_CODE_OFFSET));
        result.put("position", cellStatusMessage.getPosition());
        result.put("status", cellStatusMessage.getStatusCode());
        result.put("shipmentProcess", cellStatusMessage.getShipmentProcessCode());
        result.put("shipmentStatus", cellStatusMessage.getShipmentStatusCode());
        result.put("cellStatus", cellStatusMessage.getCellStatusCode());
        this.send(result, theme);
    }

    public void sendShipment(ShipmentMessage shipmentMessage, String theme) {
        JSONObject result = new JSONObject();
        result.put("machineNo", shipmentMessage.getMachineCode(Constants.NORMAL_MESSAGE_MACHINE_CODE_OFFSET));
        result.put("position", shipmentMessage.getPosition());
        result.put("status", shipmentMessage.getStatusCode()); // 1成功 0失败
        this.send(result, theme);
    }

    public void sendShipmentResult(ShipmentResultMessage shipmentResultMessage, String theme) {
        shipmentResultMessage.getOrderCode();
        shipmentResultMessage.getMachineCode(Constants.NORMAL_MESSAGE_MACHINE_CODE_OFFSET);
        JSONObject result = new JSONObject();
        result.put("machineNo", shipmentResultMessage.getMachineCode(Constants.NORMAL_MESSAGE_MACHINE_CODE_OFFSET));
        result.put("position", shipmentResultMessage.getPosition());
        result.put("orderCode", shipmentResultMessage.getOrderCode());
        result.put("shipmentProcess", shipmentResultMessage.getShipmentProcessCode());
        result.put("shipmentStatus", shipmentResultMessage.getShipmentStatusCode());
        result.put("cellStatus", shipmentResultMessage.getCellStatusCode());
        result.put("status", shipmentResultMessage.getStatusCode());
        result.put("payStatus", shipmentResultMessage.getPayCode());
        result.put("isDeal", shipmentResultMessage.getResultCode());
        this.send(result, theme);
    }

    public void sendHeartBeatsMessage(String machineNo) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("machineNo", machineNo);
        this.send(jsonObject, heartBeatsTopic);
    }

    private void send(JSONObject jsonObject, String topic) {
        jsonObject.put("timestamp", System.currentTimeMillis());
        jsonObject.put("expireTime", 10);
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
