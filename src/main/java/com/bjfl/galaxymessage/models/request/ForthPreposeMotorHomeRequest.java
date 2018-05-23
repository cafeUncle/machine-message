package com.bjfl.galaxymessage.models.request;

import com.bjfl.galaxymessage.models.MqRequest;

public class ForthPreposeMotorHomeRequest extends MqRequest {

    @Override
    public String toString() {
        return "ForthPreposeMotorHomeRequest{" +
                "theme='" + theme + '\'' +
                ", machineNo='" + machineNo + '\'' +
                ", machineCode='" + machineCode + '\'' +
                ", position=" + position +
                ", timestamp=" + timestamp +
                ", expireTime=" + expireTime +
                '}';
    }
}
