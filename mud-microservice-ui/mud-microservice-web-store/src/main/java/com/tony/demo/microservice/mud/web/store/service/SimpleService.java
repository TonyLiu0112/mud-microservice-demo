package com.tony.demo.microservice.mud.web.store.service;

import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.tony.demo.microservice.mud.web.store.dto.IndexDto;
import jodd.util.StringUtil;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * simple service for demo
 * <p>
 * Created by Tony on 07/04/2017.
 */
@Service
public class SimpleService {

    private final OAuth2RestTemplate restTemplate;

    public SimpleService(OAuth2RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "findCustomerAndActivityFallback",commandProperties = {
            @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")
    })
    public List<IndexDto> findCustomerAndActivity() {
        final List<IndexDto> results = new ArrayList<>();
        String responseJSON = restTemplate.getForObject("http://mud-microservice-activity/activity/listNoPage", String.class);
        JSONObject activity = JSONObject.parseObject(responseJSON);

        activity.getJSONArray("results").forEach(o -> {
            JSONObject r = (JSONObject) o;
            String activityName = findCustomerName(Long.valueOf(r.getString("id")));
            if (StringUtil.isNotEmpty(activityName)) {
                IndexDto indexDto = new IndexDto();
                indexDto.setActivityName(r.getString("name"));
                indexDto.setCustomerName(activityName);
                results.add(indexDto);
            }
        });

        return results;
    }

    public List<IndexDto> findCustomerAndActivityFallback() {
        return new ArrayList<>();
    }

    @HystrixCommand(fallbackMethod = "findCustomerNameFallback",commandProperties = {
            @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")
    })
    private String findCustomerName(Long id) {
        final String[] names = {""};
        String customers = restTemplate.getForObject("http://mud-microservice-customer/customer/activity/findByActivityId?activityId=" + id, String.class);
        JSONObject responseJSON = JSONObject.parseObject(customers);
        responseJSON.getJSONArray("results").forEach(o -> {
            JSONObject activityJSON = (JSONObject) o;
            names[0] += activityJSON.getString("name") + ",";
        });
        return names[0];
    }

    public String findCustomerNameFallback() {
        return "N/A";
    }

}
