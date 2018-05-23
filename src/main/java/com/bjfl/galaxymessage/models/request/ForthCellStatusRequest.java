package com.bjfl.galaxymessage.models.request;

import com.bjfl.galaxymessage.models.MqRequest;

public class ForthCellStatusRequest extends MqRequest {

    @Override
    public String toString() {
        return "ForthCellStatusRequest{" +
                "theme='" + theme + '\'' +
                ", machineNo='" + machineNo + '\'' +
                ", machineCode='" + machineCode + '\'' +
                ", position=" + position +
                ", timestamp=" + timestamp +
                ", expireTime=" + expireTime +
                '}';
    }
}
