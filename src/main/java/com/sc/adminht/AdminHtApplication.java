package com.sc.adminht;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication

public class AdminHtApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminHtApplication.class, args);
    }

}

