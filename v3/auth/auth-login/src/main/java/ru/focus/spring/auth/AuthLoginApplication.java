package ru.focus.spring.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class AuthLoginApplication {

    public static void main(final String[] args) {
        SpringApplication.run(AuthLoginApplication.class, args);
    }

}
