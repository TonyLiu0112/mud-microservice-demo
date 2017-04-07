package com.tony.demo.microservice.mud.web.manager.conf;

import org.apache.catalina.filters.RequestDumperFilter;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;

@Configuration
public class GeneratorConfig {

    /**
     * 请求dump过滤器，用于查看所有请求的请求头信息
     *
     * @return
     */
    @Profile("!pro") // 如果不是生产环境的profile，则打开此配置
    @Bean
    RequestDumperFilter requestDumperFilter() {
        return new RequestDumperFilter();
    }

    @Bean
    @LoadBalanced
    public OAuth2RestTemplate oauth2RestTemplate(OAuth2ClientContext oauth2ClientContext, OAuth2ProtectedResourceDetails details) {
        return new OAuth2RestTemplate(details, oauth2ClientContext);
    }

}
