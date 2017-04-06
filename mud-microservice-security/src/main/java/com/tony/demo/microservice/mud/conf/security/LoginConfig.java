package com.tony.demo.microservice.mud.conf.security;

import com.tony.demo.microservice.mud.service.SecurityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 登录权限认证配置
 * <p>
 * Created by Tony on 06/04/2017.
 */
@Configuration
public class LoginConfig extends WebSecurityConfigurerAdapter {

    private final SecurityUserService securityUserService;

    @Autowired
    public LoginConfig(SecurityUserService securityUserService) {
        this.securityUserService = securityUserService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.formLogin().loginPage("/login").permitAll()
                .and()
                .requestMatchers()
                .antMatchers("/", "/login", "/oauth/authorize", "/oauth/confirm_access")
                .and()
                .authorizeRequests()
                .anyRequest().authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityUserService.getUserDetailsService());
    }

    @Override
    @Bean
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return securityUserService.getUserDetailsService();
    }
}
