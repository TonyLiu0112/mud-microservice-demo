package com.tony.demo.microservice.mud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulServer;

@SpringBootApplication
@EnableZuulServer
@EnableEurekaClient
public class GatwayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatwayApplication.class, args);
    }
}
