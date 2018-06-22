package com.bjfl.galaxymessage.jobs;

import com.bjfl.galaxymessage.messages.Message;
import com.bjfl.galaxymessage.models.MqRequest;
import com.bjfl.galaxymessage.parser.MessageType;

public class Job {

    private MqRequest mqRequest;

    private Message nextMessage;

    private MessageType messageType;

    private long timestamp;

    private int expireTime;

    private String machineNo;

    private Message message;

    private String theme;

    private int times;

    public Job(MqRequest mqRequest, int expireTime, Message message) {
        this.mqRequest = mqRequest;
        this.timestamp = System.currentTimeMillis();
        this.expireTime = expireTime;
        this.nextMessage = message;
        if (message != null) {
            this.messageType = message.getMessageType();
        }
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public Message getNextMessage() {
        return nextMessage;
    }

    public void setNextMessage(Message nextMessage) {
        this.nextMessage = nextMessage;
        this.messageType = message.getMessageType();
    }

    public MqRequest getMqRequest() {
        return mqRequest;
    }

    public void setMqRequest(MqRequest mqRequest) {
        this.mqRequest = mqRequest;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }

    public String getMachineNo() {
        return machineNo;
    }

    public void setMachineNo(String machineNo) {
        this.machineNo = machineNo;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getTheme() {
        return this.mqRequest.getTheme();
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > (this.timestamp + this.expireTime);
    }

    @Override
    public String toString() {
        return "Job{" +
                "mqRequest=" + mqRequest +
                ", nextMessage=" + nextMessage +
                ", messageType=" + messageType +
                ", timestamp=" + timestamp +
                ", expireTime=" + expireTime +
                ", machineNo='" + machineNo + '\'' +
                ", message=" + message +
                ", theme='" + theme + '\'' +
                ", times=" + times +
                '}';
    }
}
