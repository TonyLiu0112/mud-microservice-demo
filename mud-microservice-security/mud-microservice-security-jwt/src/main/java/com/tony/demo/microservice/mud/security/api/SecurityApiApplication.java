package com.tony.demo.microservice.mud.security.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAuthorizationServer
public class SecurityApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecurityApiApplication.class, args);
    }
}