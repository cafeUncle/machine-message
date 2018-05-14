package com.bjfl.galaxymessage.controllers;

import com.bjfl.galaxymessage.util.ConfigAuto;
import com.bjfl.galaxymessage.util.NettyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class HelloController {


    @Autowired
    ConfigAuto configAuto;
    @Autowired
    NettyConfig nettyConfig;

    @RequestMapping("/hello")
    public String hello() {
        System.out.println(configAuto.host);
        return "Hello World";
    }

    @RequestMapping("/logs")
    public String logs(String machineId, Date startDatetime, Date endDatetime, Integer index) {
        System.out.println(configAuto.host);
        System.out.println(nettyConfig.port);
        return String.format("logs host:%s", configAuto.host);
    }
}
