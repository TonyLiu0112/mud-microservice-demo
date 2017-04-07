//package com.tony.demo.microservice.mud.web.manager.conf;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.BeanClassLoaderAware;
//import org.springframework.context.annotation.Bean;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializer;
//import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
//
///**
// * 分布式session配置文件
// * <p>
// * Created by Tony on 07/04/2017.
// */
//@EnableRedisHttpSession
//public class SessionConfig implements BeanClassLoaderAware {
//
//    private ClassLoader loader;
//
//    @Bean
//    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
//        return new GenericJackson2JsonRedisSerializer(objectMapper());
//    }
//
//    @Bean
//    public LettuceConnectionFactory connectionFactory() {
//        return new LettuceConnectionFactory();
//    }
//
//    ObjectMapper objectMapper() {
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModules(SecurityJackson2Modules.getModules(this.loader));
//        return mapper;
//    }
//
//    @Override
//    public void setBeanClassLoader(ClassLoader classLoader) {
//        this.loader = classLoader;
//    }
//
//}
