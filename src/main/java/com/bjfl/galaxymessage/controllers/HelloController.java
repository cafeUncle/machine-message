package com.bjfl.galaxymessage.controllers;

import com.bjfl.galaxymessage.handlers.ServerHandler;
import com.bjfl.galaxymessage.message.Message;
import com.bjfl.galaxymessage.message.MessageType;
import com.bjfl.galaxymessage.receiver.CellStatusMessage;
import com.bjfl.galaxymessage.util.ConfigAuto;
import com.bjfl.galaxymessage.util.MessageUtil;
import com.bjfl.galaxymessage.util.NettyConfig;
import com.bjfl.galaxymessage.util.Response;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;

@RestController
public class HelloController {


    @Autowired
    ConfigAuto configAuto;
    @Autowired
    NettyConfig nettyConfig;

    @RequestMapping("/")
    public String hello() {
        System.out.println(configAuto.host);
        System.out.println("new");
        System.out.println("new2");
        System.out.println("new3");
        System.out.println("new4");
        System.out.println("new5");
        return "Hello World";
    }

    /**
     * 类似于centos下载的文件服务器
     * @param machineId
     * @param startDatetime
     * @param endDatetime
     * @param index
     * @return
     */
    @RequestMapping("/logs")
    public String logs(String machineId, Date startDatetime, Date endDatetime, Integer index) {
        System.out.println(configAuto.host);
        System.out.println(nettyConfig.port);
        return String.format("logs host:%s", configAuto.host);
    }

    @RequestMapping("/cellStatus")
    public Response cellStatus(String machineCode, int cabinetAddress, int clearFlag) {
        ChannelHandlerContext channelHandlerContext = ServerHandler.clientList.get(machineCode);
//        if (channelHandlerContext == null) {
//            return new Response(false, "该机器不在线");
//        }
        try {
            CellStatusMessage cellStatusMessage = new CellStatusMessage();
            cellStatusMessage.generate(machineCode, cabinetAddress, MessageType.CELL_STATUS.getCode(), Arrays.asList(clearFlag,0x01));
            System.out.println(Arrays.toString(cellStatusMessage.getInts()));
            System.out.println(MessageUtil.intsToHexString(cellStatusMessage.getInts()));
            channelHandlerContext.channel().writeAndFlush(cellStatusMessage.getInts());
        }catch (NullPointerException e){
            e.printStackTrace();
            return new Response(false, "机器长连接已断开");
        }
        return new Response(true, "指令发送成功");
    }
}
