package com.tony.demo.microservice.mud.web.manager.conf;

import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 分布式session配置文件
 * <p>
 * Created by Tony on 07/04/2017.
 */
@EnableRedisHttpSession
public class SessionConfig {

    /**
     * 链接工厂类
     * 用于链接redis，默认localhost 6379
     * 链接信息在配置文件中已重写
     *
     * @return
     */
//    @Bean
//    public LettuceConnectionFactory connectionFactory() {
//        return new LettuceConnectionFactory();
//    }

}
