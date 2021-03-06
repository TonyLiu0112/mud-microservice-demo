package com.tony.demo.microservice.mud.services.recommendation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableDiscoveryClient
@EnableResourceServer
public class RecommendationApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecommendationApplication.class, args);
    }
}
