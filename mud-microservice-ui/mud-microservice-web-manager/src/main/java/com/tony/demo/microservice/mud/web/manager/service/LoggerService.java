package com.tony.demo.microservice.mud.web.manager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by Tony on 02/05/2017.
 */
@Service
public class LoggerService {

    private Logger logger = LoggerFactory.getLogger(LoggerService.class);

    public void print() {
        logger.info("Service INFO");
        logger.warn("Service WARN");
        logger.error("Service ERROR. {}", new NullPointerException("Service.... test log"));
        logger.debug("Service DEBUG");
    }

}
