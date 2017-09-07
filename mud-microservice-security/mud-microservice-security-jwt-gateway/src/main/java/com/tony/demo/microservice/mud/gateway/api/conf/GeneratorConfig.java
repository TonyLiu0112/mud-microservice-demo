package com.tony.demo.microservice.mud.gateway.api.conf;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GeneratorConfig {

    @Bean
    @LoadBalanced
    public RestTemplate oauth2RestTemplate() {
        return new RestTemplate();
    }

}
