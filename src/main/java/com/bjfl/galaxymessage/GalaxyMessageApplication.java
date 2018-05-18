package com.bjfl.galaxymessage;

import com.bjfl.galaxymessage.netty.MachineServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.bjfl.galaxymessage.netty", "com.bjfl.galaxymessage.controllers", "com.bjfl.galaxymessage.mqtt", "com.bjfl.galaxymessage.process", "com.bjfl.galaxymessage.util"})
public class GalaxyMessageApplication implements CommandLineRunner {

    @Autowired
    MachineServer machineServer;

    public static void main(String[] args) {
        SpringApplication.run(GalaxyMessageApplication.class, args);
    }


    /**
     * 维护一个保存 机器号-任务进度 的映射关系
     * 串联起 下发和上收的业务流程
     * 收到查货道反馈后，通过机器号获取任务进度，如有则进行，如无则该干嘛干嘛
     * @param args
     */

    @Override
    public void run(String... args) {
        try {
            machineServer.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
