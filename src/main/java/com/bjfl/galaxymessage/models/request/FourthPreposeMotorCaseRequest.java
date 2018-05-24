package com.bjfl.galaxymessage.models.request;

import com.bjfl.galaxymessage.models.MqRequest;

public class FourthPreposeMotorCaseRequest extends MqRequest {
    private int zAxis;

    public int getzAxis() {
        return zAxis;
    }

    public void setzAxis(int zAxis) {
        this.zAxis = zAxis;
    }

    @Override
    public String toString() {
        return "FourthPreposeMotorCaseRequest{" +
                "zAxis=" + zAxis +
                ", theme='" + theme + '\'' +
                ", machineNo='" + machineNo + '\'' +
                ", machineCode='" + machineCode + '\'' +
                ", position=" + position +
                ", timestamp=" + timestamp +
                ", expireTime=" + expireTime +
                '}';
    }
}
