package com.bjfl.galaxymessage;

import com.bjfl.galaxymessage.servers.DiscardServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.bjfl.galaxymessage.util", "com.bjfl.galaxymessage.controllers"})
public class GalaxyMessageApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(GalaxyMessageApplication.class, args);

        try {
            new DiscardServer(9280).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(String... args) {
        System.out.println("CommandLineRunner running");
    }
}
