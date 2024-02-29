package com.nabilaitnacer.feedsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FeedsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeedsServiceApplication.class, args);
    }

}
