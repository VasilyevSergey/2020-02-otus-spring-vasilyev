package com.otus.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableMongoRepositories
@EnableConfigurationProperties
@SpringBootApplication
@EnableWebMvc
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
