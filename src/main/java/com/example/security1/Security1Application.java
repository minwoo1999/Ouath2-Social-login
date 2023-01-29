package com.example.security1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Security1Application {

    public static void main(String[] args) {
        SpringApplication.run(Security1Application.class, args);
    }

}
