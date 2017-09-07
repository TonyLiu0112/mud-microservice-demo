package com.tony.demo.microservice.mud.security.api.conf.security;

import com.tony.demo.microservice.mud.security.api.service.SecurityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
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
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SecurityUserService securityUserService;

    @Autowired
    public WebSecurityConfig(SecurityUserService securityUserService) {
        this.securityUserService = securityUserService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/oauth/token").permitAll()
                .antMatchers(HttpMethod.POST, "/security/doLogin").permitAll()
                .antMatchers(HttpMethod.GET, "/health").permitAll()
                .anyRequest().authenticated()
                .and().httpBasic()
                .and().cors()
                .and().csrf().disable();
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

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
