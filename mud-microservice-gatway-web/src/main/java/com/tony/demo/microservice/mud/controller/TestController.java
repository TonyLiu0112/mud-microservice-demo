package com.tony.demo.microservice.mud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("demo")
public class TestController {

    private final OAuth2RestTemplate restTemplate;

    @Autowired
    public TestController(OAuth2RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/customer/list")
    public Object customerList() {
        return restTemplate.getForObject("http://localhost:8082/customer/listNoPage", Object.class);
    }

    @GetMapping("/activity/list")
    public Object activityList() {
        return restTemplate.getForObject("http://localhost:8081/activity/listNoPage", Object.class);
    }

}
