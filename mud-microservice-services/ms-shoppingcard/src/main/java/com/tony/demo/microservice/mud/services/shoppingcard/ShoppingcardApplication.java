package com.tony.demo.microservice.mud.services.shoppingcard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableDiscoveryClient
@EnableResourceServer
@EnableFeignClients
public class ShoppingcardApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShoppingcardApplication.class, args);
    }
}
