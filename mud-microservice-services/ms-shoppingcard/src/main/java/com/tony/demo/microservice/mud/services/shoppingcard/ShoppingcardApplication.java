package com.tony.demo.microservice.mud.services.shoppingcard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableEurekaClient
@EnableResourceServer
public class ShoppingcardApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShoppingcardApplication.class, args);
    }
}
