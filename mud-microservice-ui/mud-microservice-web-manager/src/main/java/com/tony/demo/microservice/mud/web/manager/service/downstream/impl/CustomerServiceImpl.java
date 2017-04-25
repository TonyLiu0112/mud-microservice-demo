package com.tony.demo.microservice.mud.web.manager.service.downstream.impl;

import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.tony.demo.microservice.mud.web.manager.dto.CustomerDto;
import com.tony.demo.microservice.mud.web.manager.dto.RestResult;
import com.tony.demo.microservice.mud.web.manager.service.downstream.CustomerService;
import com.tony.demo.microservice.mud.web.manager.service.downstream.fallback.CustomerServiceFallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl extends CustomerServiceFallback implements CustomerService {

    private Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final RestTemplate restTemplate;

    @Autowired
    public CustomerServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "findByActivityIdFallback", commandProperties = {@HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")})
    @Override
    public Optional<List<CustomerDto>> findByActivityId(long id) throws Exception {
        try {
            String responseText = restTemplate.getForObject(String.format(URI, "customer/activity/findByActivityId?activityId=" + id), String.class);
            RestResult restResult = JSONObject.parseObject(responseText, RestResult.class);
            List<CustomerDto> customerDtos = JSONObject.parseArray(restResult.getResults().toString(), CustomerDto.class);
            return Optional.of(customerDtos);
        } catch (RestClientException e) {
            logger.error("", e);
            throw e;
        }
    }
}
