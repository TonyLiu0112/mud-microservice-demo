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
import java.util.Random;

@Service
public class ActivityServiceImpl extends ActivityServiceFallback implements ActivityService {

    private Logger logger = LoggerFactory.getLogger(ActivityServiceImpl.class);

    private final OAuth2RestTemplate restTemplate;

    @Autowired
    public ActivityServiceImpl(OAuth2RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(defaultFallback = "findAllActivityFallBack", commandProperties = {
            @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
            // 当在配置时间窗口(默认十秒一个窗口)内达到此数量的失败后，进行短路，默认20个。
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
            // 熔断器默认工作时间,默认:5秒.熔断器中断请求后的10秒内会进入半打开状态,10秒后会放部分请求通过尝试。
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
            // 熔断器容错比率，默认50%。意味着在时间窗口内，有50%的请求失败，就会熔断。
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "30"),
            // 熔断器的窗口时间范围，默认10秒一个窗口
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "20000")
    })
    @Override
    public Optional<List<ActivityDto>> findAllActivity() throws Exception {
        try {
            Random random = new Random();
            if (random.nextInt(100) > 50) // mock access downstream service exception.
                throw new RuntimeException("Mock exception.");
            String responseText = restTemplate.getForObject(String.format(URI, "activity/listNoPage"), String.class);
            RestResult restResult = JSONObject.parseObject(responseText, RestResult.class);
            List<ActivityDto> activityDtoList = JSONObject.parseArray(restResult.getResults().toString(), ActivityDto.class);
            return Optional.of(activityDtoList);
        } catch (Exception e) {
            logger.error("Internal error.", e);
            throw e;
        }
    }

    @HystrixCommand(defaultFallback = "findByNameFallback",
            commandProperties = @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"))
    @Override
    public Optional<ActivityDto> findByName(String name) throws Exception {
        String responseText = restTemplate.getForObject(String.format(URI, "activity/name/" + name), String.class);
        RestResult restResult = JSONObject.parseObject(responseText, RestResult.class);
        ActivityDto activityDto = JSONObject.parseObject(restResult.getResults().toString(), ActivityDto.class);
        return Optional.of(activityDto);
    }

}
