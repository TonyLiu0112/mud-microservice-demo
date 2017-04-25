package com.tony.demo.microservice.mud.web.manager.service.downstream.impl;

import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.tony.demo.microservice.mud.web.manager.dto.ActivityDto;
import com.tony.demo.microservice.mud.web.manager.dto.RestResult;
import com.tony.demo.microservice.mud.web.manager.service.downstream.ActivityService;
import com.tony.demo.microservice.mud.web.manager.service.downstream.fallback.ActivityServiceFallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityServiceImpl extends ActivityServiceFallback implements ActivityService {

    private Logger logger = LoggerFactory.getLogger(ActivityServiceImpl.class);

    private final OAuth2RestTemplate restTemplate;

    @Autowired
    public ActivityServiceImpl(OAuth2RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(defaultFallback = "findAllActivityFallBack", commandProperties = {@HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")})
    @Override
    public Optional<List<ActivityDto>> findAllActivity() throws Exception {
        try {
            String responseText = restTemplate.getForObject(String.format(URI, "activity/listNoPage"), String.class);
            RestResult restResult = JSONObject.parseObject(responseText, RestResult.class);
            List<ActivityDto> activityDtoList = JSONObject.parseArray(restResult.getResults().toString(), ActivityDto.class);
            return Optional.of(activityDtoList);
        } catch (Exception e) {
            logger.error("*******", e);
            throw e;
        }
    }

}
