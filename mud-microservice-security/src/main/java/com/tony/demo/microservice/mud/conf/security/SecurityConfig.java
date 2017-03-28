//package com.tony.demo.microservice.mud.conf.security;
//
//import com.tony.demo.microservice.mud.service.SecurityUserService;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.security.web.DefaultRedirectStrategy;
//import org.springframework.security.web.RedirectStrategy;
//import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
//import org.springframework.security.web.session.InvalidSessionStrategy;
//import org.springframework.util.StringUtils;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * Security
// * <p>
// * Created by Tony on 19/01/2017.
// */
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    private final SecurityUserService securityUserService;
//
//    @Autowired
//    public SecurityConfig(SecurityUserService securityUserService) {
//        this.securityUserService = securityUserService;
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//
//        // entry point
//        http.exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPointImpl());
//        String[] permitAll = {"/security/**", "/image/**"};
//        http.authorizeRequests()
//                .antMatchers(permitAll).permitAll()
//                .antMatchers("/**").hasRole("USER")
//                .anyRequest().authenticated()
//
//                // session time out
//                .and()
//                .sessionManagement()
//                .invalidSessionStrategy(new InvalidSessionStrategyImpl())
//                .invalidSessionUrl("/security/session_timeout")
//
//                .and().cors()
//
//                // login
//                .and()
//                .formLogin()
//                .successHandler(new SuccessLoginHandler())
//                .failureHandler(new FailureLoginHandler())
//                .loginPage("/security/login")
//                .permitAll()
//
//                // logout
//                .and()
//                .logout().permitAll();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(securityUserService.getUserDetailsService());
//    }
//
//    private static class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
//        private Logger logger = Logger.getLogger(AuthenticationEntryPointImpl.class);
//
//        @Override
//        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//            logger.error("认证入口错误:", authException);
//            response.setStatus(HttpServletResponse.SC_OK);
//            response.getWriter().print("{\"session_invalid\": true}");
//            response.getWriter().flush();
//        }
//    }
//
//    private static class InvalidSessionStrategyImpl implements InvalidSessionStrategy {
//        private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
//
//        @Override
//        public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//            if (request.getHeader("x-requested-with") != null) {
//                response.setHeader("session_status", "timeout");
//                response.getWriter().print("{\"session_invalid\": true}");
//            } else {
//                request.getSession();
//                redirectStrategy.sendRedirect(request, response, "/security/session_timeout");
//            }
//        }
//    }
//
//    private static class FailureLoginHandler extends SimpleUrlAuthenticationFailureHandler {
//        @Override
//        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//            String requestType = request.getHeader("x-requested-with");
//            if (!StringUtils.isEmpty(requestType)) {
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                response.getWriter().print("{\"success\": false}");
//            } else {
//                setDefaultFailureUrl("/security/login?error=true");
//                super.onAuthenticationFailure(request, response, exception);
//            }
//        }
//    }
//
//    private static class SuccessLoginHandler extends SavedRequestAwareAuthenticationSuccessHandler {
//        @Override
//        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//            String requestType = request.getHeader("x-requested-with");
//            if (!StringUtils.isEmpty(requestType)) {
//                response.setStatus(HttpServletResponse.SC_OK);
//                response.getWriter().print("{\"success\": true}");
//            } else {
//                setDefaultTargetUrl("/index/index");
//                setAlwaysUseDefaultTargetUrl(true);
//                super.onAuthenticationSuccess(request, response, authentication);
//            }
//        }
//    }
//}
