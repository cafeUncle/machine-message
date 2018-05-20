package com.bjfl.galaxymessage.controllers;

import com.bjfl.galaxymessage.util.ConfigAuto;
import com.bjfl.galaxymessage.util.NettyConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class HelloController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ConfigAuto configAuto;
    @Autowired
    NettyConfig nettyConfig;

    @RequestMapping("/")
    public String hello() {
        logger.info("Hello World");
        logger.info(configAuto.username);
        logger.info(configAuto.password);
        logger.info(configAuto.toString());
        return "index.html";
    }

    /**
     * 类似于centos下载的文件服务器
     *
     * @param startDatetime
     * @param endDatetime
     * @return
     */
    @RequestMapping("/logs")
    @ResponseBody
    public String logs(Date startDatetime, Date endDatetime) {
        return String.format("logs host:%s", configAuto.host);
    }

}
