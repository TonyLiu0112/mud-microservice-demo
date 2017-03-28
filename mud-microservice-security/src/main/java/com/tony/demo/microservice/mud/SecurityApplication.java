package com.tony.demo.microservice.mud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableEurekaClient
public class SecurityApplication extends WebMvcConfigurerAdapter {
    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }
}