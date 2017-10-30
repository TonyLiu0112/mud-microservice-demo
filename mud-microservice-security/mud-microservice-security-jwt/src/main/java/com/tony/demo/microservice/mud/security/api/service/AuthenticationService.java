package com.tony.demo.microservice.mud.security.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tony.demo.microservice.mud.security.api.service.bean.SecurityUserReq;
import com.tony.demo.microservice.mud.security.api.service.bean.UserWrapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.*;

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
    private final RestTemplate restTemplate;
    private final LoadBalancerClient loadBalancer;

    @Value("${spring.application.name}")
    private String authServerId;

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager, SecurityUserService securityUserService, RestTemplate restTemplate, LoadBalancerClient loadBalancer) {
        this.authenticationManager = authenticationManager;
        this.securityUserService = securityUserService;
        this.restTemplate = restTemplate;
        this.loadBalancer = loadBalancer;
    }

    public Optional<UserWrapper> doAuthentication(SecurityUserReq userReq) {
        try {
            UserWrapper userWrapper = securityUserService.findUserDetails(userReq.getLoginName());
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userWrapper.getUser(), userReq.getPassword(), userWrapper.getUser().getAuthorities());
            authenticationManager.authenticate(token);
            if (token.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(token);
                return Optional.of(userWrapper);
            }
        } catch (AuthenticationException e) {
            logger.error("Failed to authentication", e);
        }
        return Optional.empty();
    }

    public HashMap obtainJwtToken(String clientId, String secret, UserWrapper user) throws Exception {
        TokenBuild tokenBuild = new TokenBuild();
        return tokenBuild.setToken(clientId, secret, user).build();
    }

    class TokenBuild {

        HashMap jwtMap = new HashMap();

        HashMap build() {
            return jwtMap;
        }

        TokenBuild setToken(String clientId, String secret, UserWrapper user) throws Exception {
            setInterceptor(clientId, secret);
            ResponseEntity<String> response = restTemplate.postForEntity(getAuthURI(clientId, user), null, String.class);
            String responseText = response.getBody();
            Assert.isTrue(HttpStatus.OK == response.getStatusCode(), "Request oauth/token error, response is " + response.getStatusCode());
            jwtMap = new ObjectMapper().readValue(responseText, HashMap.class);
            Assert.isTrue(jwtMap.containsKey("access_token"), "No access_token in jwt response.");
            return this;
        }

        private String getAuthURI(String clientId, UserWrapper user) {
            ServiceInstance instance = loadBalancer.choose(authServerId);
            String basicUrl = String.format("http://%s:%s/oauth/token", instance.getHost(), instance.getPort());
            String userReq = String.format("&user_id=%s&user_name=%s&nickname=%s&email=%s&phone=%s&sex=%s&roles=%s",
                    user.getId(), user.getLoginName(), user.getNickname(), user.getEmail(), user.getPhone(), user.getSex(),
                    user.getUser().getAuthorities());
            return basicUrl + "?client_id=" + clientId + "&grant_type=client_credentials" + userReq;
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
