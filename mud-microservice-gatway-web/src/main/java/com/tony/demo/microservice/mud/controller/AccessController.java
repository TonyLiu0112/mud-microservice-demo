package com.tony.demo.microservice.mud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("demo")
public class AccessController {

    private final OAuth2RestTemplate restTemplate;

    @Autowired
    public AccessController(OAuth2RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/customer/list")
    public Object customerList() {
        return restTemplate.getForObject("http://mud-microservice-customer/customer/listNoPage", Object.class);
    }

    @GetMapping("/activity/list")
    public Object activityList() {
        return restTemplate.getForObject("http://mud-microservice-activity/activity/listNoPage", Object.class);
    }

    /**
     * 私有资源
     * 仅拥有admin权限用户才可访问
     *
     * @param activityId
     * @return
     */
    @GetMapping("/list/security")
    public Object adminList(@RequestParam("activityId") String activityId) {
        return restTemplate.getForObject("http://mud-microservice-activity/activity/findAll?activityId=" + activityId, Object.class);
    }

}
