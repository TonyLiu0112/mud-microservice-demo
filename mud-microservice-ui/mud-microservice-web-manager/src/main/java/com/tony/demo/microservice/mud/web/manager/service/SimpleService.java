package com.tony.demo.microservice.mud.web.manager.service;

import com.tony.demo.microservice.mud.web.manager.dto.ActivityDto;
import com.tony.demo.microservice.mud.web.manager.dto.CustomerDto;
import com.tony.demo.microservice.mud.web.manager.dto.IndexDto;
import com.tony.demo.microservice.mud.web.manager.service.downstream.ActivityService;
import com.tony.demo.microservice.mud.web.manager.service.downstream.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * simple service for demo
 * <p>
 * Created by Tony on 07/04/2017.
 */
@Service
public class SimpleService {

    private Logger logger = LoggerFactory.getLogger(SimpleService.class);

    private final CustomerService customerService;
    private final ActivityService activityService;

    @Autowired
    public SimpleService(CustomerService customerService, ActivityService activityService) {
        this.customerService = customerService;
        this.activityService = activityService;
    }

    /**
     * dddd
     * @return
     * @throws Exception
     */
    public List<IndexDto> findCustomerAndActivity() throws Exception {
        final List<IndexDto> results = new ArrayList<>();
        activityService.findAllActivity().ifPresent(activityDtos -> activityDtos.forEach(activityDto -> {
            IndexDto indexDto = new IndexDto();
            indexDto.setActivityName(activityDto.getName());
            indexDto.setCustomerName(getCustomerNames(activityDto.getId()));
            results.add(indexDto);
        }));
        return results;
    }

    private String getCustomerNames(Long id) {
        try {
            final String[] names = {"无数据"};
            customerService.findByActivityId(id)
                    .ifPresent(customerDtos -> {
                        if (!customerDtos.isEmpty())
                            names[0] = customerDtos.stream().map(CustomerDto::getName).collect(Collectors.joining(","));
                    });
            return names[0];
        } catch (Exception e) {
            logger.error("Failed to load customer Name." + e);
        }
        return "ERROR";
    }

    public ActivityDto findActivityByName(String name) throws Exception {
        return activityService.findByName(name).orElse(new ActivityDto());
    }

}
