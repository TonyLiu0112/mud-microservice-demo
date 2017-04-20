package com.tony.demo.microservice.mud.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tony.demo.microservice.mud.service.dto.SecurityUserReq;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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
    private final TokenBuild tokenBuild;

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager, SecurityUserService securityUserService) {
        this.authenticationManager = authenticationManager;
        this.securityUserService = securityUserService;
        this.tokenBuild = new TokenBuild();
    }

    public boolean doAuthentication(SecurityUserReq userReq) {
        try {
            UserDetails userDetails = securityUserService.findUserDetails(userReq.getLoginName());
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, userReq.getPassword(), userDetails.getAuthorities());
            authenticationManager.authenticate(token);
            if (token.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(token);
                return true;
            }
        } catch (AuthenticationException e) {
            logger.error("Failed to authentication", e);
        }
        return false;
    }

    public HashMap obtainJwtToken(String username, String password) throws Exception {
        return tokenBuild.buildToken(username, password);
    }

    class TokenBuild {

        private RestTemplate restTemplate;

        TokenBuild() {
            this.restTemplate = new RestTemplate();
        }

        HashMap buildToken(String username, String password) throws Exception {
            setInterceptor(username, password);
            ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:9998/oauth/token?client_id=acme&grant_type=client_credentials", null, String.class);
            String responseText = response.getBody();
            Assert.isTrue(HttpStatus.OK == response.getStatusCode(), "Request oauth/token error, response is " + response.getStatusCode());
            HashMap jwtMap = new ObjectMapper().readValue(responseText, HashMap.class);
            Assert.isTrue(jwtMap.containsKey("access_token"), "No access_token in jwt response.");
            return jwtMap;
        }

        private void setInterceptor(String username, String password) {
            Assert.notNull(username, "username is null");
            Assert.notNull(password, "password is null");
            List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
            if (interceptors == null)
                interceptors = Collections.emptyList();
            interceptors = new ArrayList<>(interceptors);
            interceptors.removeIf(clientHttpRequestInterceptor
                    -> clientHttpRequestInterceptor instanceof BasicAuthorizationInterceptor);
            interceptors.add(new BasicAuthorizationInterceptor(username, password));
            restTemplate.setInterceptors(interceptors);
        }

    }

}
