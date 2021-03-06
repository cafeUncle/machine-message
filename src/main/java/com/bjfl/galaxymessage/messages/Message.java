package com.bjfl.galaxymessage.messages;

import com.bjfl.galaxymessage.parser.MessageType;
import com.bjfl.galaxymessage.util.MessageUtil;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Message implements Serializable {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected int[] ints;

    public Message() {
    }

    public Message(int[] ints) {
        super();
        this.ints = ints;
    }

    public int[] getInts() {
        return ints;
    }

    public void setInts(int[] ints) {
        this.ints = ints;
    }

    public MessageType getMessageType() {
        return MessageType.getEnum(this.ints[4] - 0x80);
    }

    public String getMachineCode(int offset) {
        return new String(this.ints, offset, 13);
    }

    public int getPosition() {
        return this.ints[19];
    }

    public void print(ChannelHandlerContext ctx) {

    }

    public void generate(String machineCode, int cabinetAddress, int command, List<Integer> data) {
        List<Integer> msg = new ArrayList<>(Arrays.asList(0x8E, 0x00, 0x00));
        List<Integer> machineCodeHex = MessageUtil.strTo16(machineCode);
        msg.addAll(machineCodeHex);
        msg.add(cabinetAddress);
        msg.add(command);
//        msg.add(0x00); //数据01, 主机地址, 暂不关注, 默认 0x00
        msg.addAll(data);
        //更改长度
        msg.set(1, (msg.size() + 2) >>> 8);
        msg.set(2, (msg.size() + 2) & 0xff);
        //校验和
        msg.add(msg.stream().reduce((a, b) -> a + b).get() % 256);
        msg.add(0xED);
        this.ints = new int[msg.size()];
        for (int i = 0; i < msg.size(); i++) {
            this.ints[i] = msg.get(i);
        }
    }

}
