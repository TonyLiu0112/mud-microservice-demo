package com.tony.demo.microservice.mud.web.manager.service.downstream.fallback;

import com.tony.demo.microservice.mud.web.manager.dto.ActivityDto;
import com.tony.demo.microservice.mud.web.manager.service.downstream.ActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class ActivityServiceFallback {

    private Logger logger = LoggerFactory.getLogger(ActivityServiceFallback.class);

    public Optional<List<ActivityDto>> findAllActivityFallBack() {
        logger.warn("The method of findAllActivity() is not available. Please check the downstream service of {}", ActivityService.URI);
        return Optional.empty();
    }

    public Optional<ActivityDto> findByNameFallback() {
        logger.warn("The method of findByName() is not available. Please check the downstream service of {}", ActivityService.URI);
        return Optional.empty();
    }
}