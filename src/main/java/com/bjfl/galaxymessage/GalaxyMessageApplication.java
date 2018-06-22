package com.bjfl.galaxymessage;

import com.bjfl.galaxymessage.netty.NettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan({
        "com.bjfl.galaxymessage.controllers",
        "com.bjfl.galaxymessage.netty",
        "com.bjfl.galaxymessage.mqtt",
        "com.bjfl.galaxymessage.jobs",
        "com.bjfl.galaxymessage.websocket",
        "com.bjfl.galaxymessage.util"})
@EnableScheduling
public class GalaxyMessageApplication implements CommandLineRunner {

    @Autowired
    NettyServer nettyServer;

    public static void main(String[] args) {
        SpringApplication.run(GalaxyMessageApplication.class, args);
    }

    @Override
    public void run(String... args) {
        try {
            nettyServer.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
