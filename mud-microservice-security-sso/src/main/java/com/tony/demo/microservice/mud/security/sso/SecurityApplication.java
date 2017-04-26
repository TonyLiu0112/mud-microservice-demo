package com.tony.demo.microservice.mud.security.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableEurekaClient
@RestController
@EnableAuthorizationServer
public class SecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }

    @Configuration
    static class MvcConfig extends WebMvcConfigurerAdapter {
        @Override
        public void addViewControllers(ViewControllerRegistry registry) {
            registry.addViewController("login").setViewName("login");
            registry.addViewController("/").setViewName("index");
        }
    }

    @GetMapping("/test")
    public String test() {
        return "success";
    }

//    @Profile("!cloud")
//    @Bean
//    RequestDumperFilter requestDumperFilter() {
//        return new RequestDumperFilter();
//    }

}