package com.bjfl.galaxymessage.controllers;

import com.bjfl.galaxymessage.util.ConfigAuto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class HelloController {


    @Autowired
    ConfigAuto configAuto;

    @RequestMapping("hello")
    public Date hello() {
        System.out.println(configAuto.host);
        return new Date();
    }
}
