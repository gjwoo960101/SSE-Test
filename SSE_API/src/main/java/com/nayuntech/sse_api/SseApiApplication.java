package com.nayuntech.sse_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SseApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SseApiApplication.class, args);
    }

}
