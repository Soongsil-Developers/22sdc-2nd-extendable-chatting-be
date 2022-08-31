package com.example.extendablechattingbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ExtendableChattingBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExtendableChattingBeApplication.class, args);
    }

}
