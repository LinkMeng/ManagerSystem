package com.linkmeng.managersystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ManagerSystemApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ManagerSystemApplication.class, args);
        Runtime.getRuntime().addShutdownHook(new Thread(context::close));
    }
}
