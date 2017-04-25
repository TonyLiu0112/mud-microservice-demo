package com.tony.demo.microservice.mud.web.manager.service.downstream.fallback;

import com.tony.demo.microservice.mud.web.manager.dto.CustomerDto;
import com.tony.demo.microservice.mud.web.manager.service.downstream.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class CustomerServiceFallback {

    private Logger logger = LoggerFactory.getLogger(CustomerServiceFallback.class);

    public Optional<List<CustomerDto>> findByActivityIdFallback(long id) {
        logger.warn("The method of findByActivityId() is not available. Please check the downstream service of {}", CustomerService.URI);
        return Optional.empty();
    }

}
