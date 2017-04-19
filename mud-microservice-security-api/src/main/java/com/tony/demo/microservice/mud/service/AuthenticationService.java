package com.tony.demo.microservice.mud.service;

import com.tony.demo.microservice.mud.service.dto.SecurityUserReq;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * 登入认证服务
 * <p>
 * Created by Tony on 27/02/2017.
 */
@Service
public class AuthenticationService {

    private Logger logger = Logger.getLogger(AuthenticationService.class);

    private final AuthenticationManager authenticationManager;

    private final SecurityUserService securityUserService;

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager, SecurityUserService securityUserService) {
        this.authenticationManager = authenticationManager;
        this.securityUserService = securityUserService;
    }

    public boolean doAuthentication(SecurityUserReq userReq) {
        try {
            UserDetails userDetails = securityUserService.findUserDetails(userReq.getLoginName());
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, userReq.getPassword(), userDetails.getAuthorities());
            authenticationManager.authenticate(token);
            System.out.println("");
            if (token.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(token);
                return true;
            }
        } catch (AuthenticationException e) {
            logger.error("Failed to authentication", e);
        }
        return false;
    }

}
