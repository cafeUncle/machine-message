package com.bjfl.galaxymessage;

import com.bjfl.galaxymessage.util.ConfigAuto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ComponentScan({"com.bjfl.galaxymessage.util", "com.bjfl.galaxymessage.controllers"})
public class GalaxyMessageApplication {

    public static void main(String[] args) {
        SpringApplication.run(GalaxyMessageApplication.class, args);
    }
}
