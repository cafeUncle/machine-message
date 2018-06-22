package com.bjfl.galaxymessage.controllers;

import com.bjfl.galaxymessage.util.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @Autowired
    AdminConfig adminConfig;

    @RequestMapping("/")
    public String hello() {
        return "index.html";
    }

    /**
     * 类似于centos下载的文件服务器
     *
     * @return
     */
    @RequestMapping("/logs")
    @ResponseBody
    public String logs() {
        return String.format("logs host:%s", adminConfig.host);
    }

}
