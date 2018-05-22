package com.bjfl.galaxymessage.controllers;

import com.bjfl.galaxymessage.messages.*;
import com.bjfl.galaxymessage.netty.NettyMessageHandler;
import com.bjfl.galaxymessage.parser.MessageType;
import com.bjfl.galaxymessage.util.MessageUtil;
import com.bjfl.galaxymessage.util.Response;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class TestController {

    @Autowired
    NettyMessageHandler nettyMessageHandler;

    /**
     * 复位单片机系统  复位前先查货道状态
     * @param machineCode
     * @param cabinetAddress 机柜编号
     * @param device         01 货道主板  02 通讯主板
     * @return
     */
    @RequestMapping("/reset")
    public Response reset(String machineCode, int cabinetAddress, int device) {
        ChannelHandlerContext channelHandlerContext = NettyMessageHandler.clientList.get(machineCode);
        if (channelHandlerContext == null) {
            return new Response(false, "该机器未注册");
        }
        try {
            ResetMessage resetMessage = new ResetMessage();
            resetMessage.generate(machineCode, cabinetAddress, MessageType.RESET.getCode(), Arrays.asList(device, 0x00));

            if (channelHandlerContext.channel().isActive() && channelHandlerContext.channel().isWritable()) {
                nettyMessageHandler.channelWrite(channelHandlerContext, resetMessage);
            }else {
                return new Response(false, "机器长连接已断开");
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
            return new Response(false, "机器长连接已断开");
        }
        return new Response(true, "指令发送成功");
    }

    /**
     * 查询货道状态
     * @param machineCode
     * @param cabinetAddress 机柜编号
     * @param clearFlag      是否清空上次的出货结果  0 清空 1 保持，不清空
     * @return
     */
    @RequestMapping("/cellStatus")
    public Response cellStatus(String machineCode, int cabinetAddress, int clearFlag) {
        ChannelHandlerContext channelHandlerContext = NettyMessageHandler.clientList.get(machineCode);
        if (channelHandlerContext == null) {
            return new Response(false, "该机器未注册");
        }
        try {
            CellStatusMessage cellStatusMessage = new CellStatusMessage();
            cellStatusMessage.generate(machineCode, cabinetAddress, MessageType.CELL_STATUS.getCode(), Arrays.asList(clearFlag, 0x01));

            if (channelHandlerContext.channel().isActive() && channelHandlerContext.channel().isWritable()) {
                nettyMessageHandler.channelWrite(channelHandlerContext, cellStatusMessage);
            }else {
                return new Response(false, "机器长连接已断开");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            return new Response(false, "机器长连接已断开");
        }
        return new Response(true, "指令发送成功");
    }

    /**
     * 整体出货指令  出货前先查货道状态
     * @param machineCode
     * @param cabinetAddress
     * @param orderCode
     * @param f 前置电机坐标
     * @param b 后置电机坐标
     * @param lr 左右电机坐标
     * @return
     */
    @RequestMapping("/shipment")
    public Response shipment(String machineCode, int cabinetAddress, String orderCode, int f, int b, int lr) {
        ChannelHandlerContext channelHandlerContext = NettyMessageHandler.clientList.get(machineCode);
        if (channelHandlerContext == null) {
            return new Response(false, "该机器未注册");
        }
        try {
            List<Integer> data = new ArrayList<>();
            data.addAll(MessageUtil.strTo16(orderCode));
            data.addAll(Arrays.asList(0x01, f >>> 8, f & 0xff, 0x02, b >>> 8, b & 0xff, 0x03, lr >>> 8, lr & 0xff));

            ShipmentMessage shipmentMessage = new ShipmentMessage();
            shipmentMessage.generate(machineCode, cabinetAddress, MessageType.SHIPMENT.getCode(), data);

            if (channelHandlerContext.channel().isActive() && channelHandlerContext.channel().isWritable()) {
                nettyMessageHandler.channelWrite(channelHandlerContext, shipmentMessage);
            }else {
                return new Response(false, "机器长连接已断开");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            return new Response(false, "机器长连接已断开");
        }
        return new Response(true, "指令发送成功");
    }

    /**
     * 查询出货结果  出货指令发出一秒后，开始查询出货结果，每秒轮询一次
     * @param machineCode
     * @param cabinetAddress
     * @param isDeal  当前出货结果是否处理   1 未处理  0 已处理   收到出货完成状态后是否要再发一次携带已处理标志的指令？
     * @return
     */
    @RequestMapping("/shipmentResult")
    public Response shipmentResult(String machineCode, int cabinetAddress, int isDeal) {
        ChannelHandlerContext channelHandlerContext = NettyMessageHandler.clientList.get(machineCode);
        if (channelHandlerContext == null) {
            return new Response(false, "该机器未注册");
        }
        try {
            ShipmentResultMessage shipmentResultMessage = new ShipmentResultMessage();
            shipmentResultMessage.generate(machineCode, cabinetAddress, MessageType.SHIPMENT_RESULT.getCode(), Arrays.asList(isDeal, 0x00));

            if (channelHandlerContext.channel().isActive() && channelHandlerContext.channel().isWritable()) {
                nettyMessageHandler.channelWrite(channelHandlerContext, shipmentResultMessage);
            }else {
                return new Response(false, "机器长连接已断开");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            return new Response(false, "机器长连接已断开");
        }
        return new Response(true, "指令发送成功");
    }

    /**
     * 获取出货日志  底层只保存了最近8次的日志   调用之前先查货道状态
     * @param machineCode
     * @param cabinetAddress
     * @param orderCode  订单编号   必须是25位
     * @param times      表示要读取第几次日志，默认为0x00，跟订单编号的读取方式互斥，两个只能用一个，同时使用时，按订单编号的读取方式优先进行
     * @return
     */
    @RequestMapping("/shipmentLog")
    public Response shipmentLog(String machineCode, int cabinetAddress, String orderCode, @RequestParam(required = false, defaultValue = "0") Integer times) {
        ChannelHandlerContext channelHandlerContext = NettyMessageHandler.clientList.get(machineCode);
        if (channelHandlerContext == null) {
            return new Response(false, "该机器未注册");
        }
        try {
            List<Integer> data = new ArrayList<>();
            if (StringUtils.isEmpty(orderCode)) {
                data.addAll(Arrays.asList(
                                0x00,0x00,0x00,0x00,0x00,
                                0x00,0x00,0x00,0x00,0x00,
                                0x00,0x00,0x00,0x00,0x00,
                                0x00,0x00,0x00,0x00,0x00,
                                0x00,0x00,0x00,0x00,0x00));
            }else {
                data.addAll(MessageUtil.strTo16(orderCode));
            }
            data.addAll(Arrays.asList(times, 0x00));

            ShipmentLogMessage shipmentLogMessage = new ShipmentLogMessage();
            shipmentLogMessage.generate(machineCode, cabinetAddress, MessageType.SHIPMENT_LOG.getCode(), data);
            if (channelHandlerContext.channel().isActive() && channelHandlerContext.channel().isWritable()) {
                nettyMessageHandler.channelWrite(channelHandlerContext, shipmentLogMessage);
            }else {
                return new Response(false, "机器长连接已断开");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            return new Response(false, "机器长连接已断开");
        }
        return new Response(true, "指令发送成功");
    }

}
