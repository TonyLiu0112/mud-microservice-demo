package com.tony.demo.microservice.mud.api.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
@EnableAuthorizationServer
public class SecurityApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecurityApiApplication.class, args);
    }
}