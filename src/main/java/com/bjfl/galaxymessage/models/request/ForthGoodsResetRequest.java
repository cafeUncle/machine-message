package com.bjfl.galaxymessage.models.request;

import com.bjfl.galaxymessage.models.MqRequest;

public class ForthGoodsResetRequest extends MqRequest {

    @Override
    public String toString() {
        return "ForthGoodsResetRequest{" +
                "theme='" + theme + '\'' +
                ", machineNo='" + machineNo + '\'' +
                ", machineCode='" + machineCode + '\'' +
                ", position=" + position +
                ", timestamp=" + timestamp +
                ", expireTime=" + expireTime +
                '}';
    }
}
