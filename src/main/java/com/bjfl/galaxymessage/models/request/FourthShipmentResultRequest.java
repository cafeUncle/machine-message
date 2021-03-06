package com.bjfl.galaxymessage.models.request;

import com.bjfl.galaxymessage.models.MqRequest;

public class FourthShipmentResultRequest extends MqRequest {

    private Integer orderId;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "FourthShipmentResultRequest{" +
                "orderId=" + orderId +
                ", theme='" + theme + '\'' +
                ", machineNo='" + machineNo + '\'' +
                ", machineCode='" + machineCode + '\'' +
                ", position=" + position +
                ", timestamp=" + timestamp +
                ", expireTime=" + expireTime +
                '}';
    }
}
