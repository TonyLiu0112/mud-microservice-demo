package com.tony.demo.microservice.mud.thirdparty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * boot class
 * <p>
 * Created by Tony on 11/06/2017.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableResourceServer
public class ThirdpartyApplication {
    public static void main(String[] args) {
        SpringApplication.run(ThirdpartyApplication.class, args);
    }
}
