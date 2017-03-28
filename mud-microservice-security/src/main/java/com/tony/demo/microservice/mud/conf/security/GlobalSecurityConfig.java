//package com.tony.demo.microservice.mud.conf.security;
//
//import com.tony.demo.microservice.mud.service.SecurityUserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//
///**
// * Global security configuration.
// * <p>
// * Created by Tony on 14/02/2017.
// */
//@Configuration
//public class GlobalSecurityConfig extends GlobalAuthenticationConfigurerAdapter {
//
//    private final SecurityUserService securityUserService;
//
//    @Autowired
//    public GlobalSecurityConfig(SecurityUserService securityUserService) {
//        this.securityUserService = securityUserService;
//    }
//
//    @Override
//    public void init(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService());
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return securityUserService.getUserDetailsService();
//    }
//}
