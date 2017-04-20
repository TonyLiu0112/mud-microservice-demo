package com.tony.demo.microservice.mud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("demo")
public class AccessController {

    private final RestTemplate restTemplate;

    private final TokenStore tokenStore;

    @Autowired
    public AccessController(RestTemplate restTemplate, TokenStore tokenStore) {
        this.restTemplate = restTemplate;
        this.tokenStore = tokenStore;
    }

    @GetMapping("/customer/list")
    public Object customerList() {
        return restTemplate.getForObject("http://mud-microservice-customer/customer/listNoPage", Object.class);
    }

    @GetMapping("/activity/list")
    public Object activityList() {
        return restTemplate.getForObject("http://mud-microservice-activity/activity/listNoPage", Object.class);
    }

    @GetMapping("/list/security")
    public Object adminList(@RequestParam("activityId") String activityId) {
        return restTemplate.getForObject("http://mud-microservice-activity/activity/findAll?activityId=" + activityId, Object.class);
    }

    @GetMapping("/tokens")
    public List<String> getTokens() {
        return tokenStore.findTokensByClientId("123").stream().map(OAuth2AccessToken::getValue).collect(Collectors.toList());
    }


}
