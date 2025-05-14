package com.example.mdbspringbootreactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class MdbSpringBootReactiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(MdbSpringBootReactiveApplication.class, args);
    }

}
