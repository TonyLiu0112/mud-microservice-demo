package com.tony.demo.microservice.mud.web.manager.controller;

import com.tony.demo.microservice.mud.common.AbstractController;
import com.tony.demo.microservice.mud.web.manager.service.LoggerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by Tony on 02/05/2017.
 */
@RestController
@RequestMapping("log")
public class LogController extends AbstractController {

    private Logger logger = LoggerFactory.getLogger(LogController.class);

    private final LoggerService loggerService;

    @Autowired
    public LogController(LoggerService loggerService) {
        this.loggerService = loggerService;
    }

    @GetMapping("print")
    public Map<String, Object> print() {
        logger.info("controller INFO");
        logger.warn("controller WARN");
        logger.error("controller ERROR. {}", new NullPointerException("test log"));
        logger.debug("controller DEBUG");
        loggerService.print();
        return success();
    }

}
