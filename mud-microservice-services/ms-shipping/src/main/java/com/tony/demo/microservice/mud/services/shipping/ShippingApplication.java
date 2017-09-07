package com.tony.demo.microservice.mud.services.shipping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableDiscoveryClient
@EnableResourceServer
public class ShippingApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShippingApplication.class, args);
    }
}
