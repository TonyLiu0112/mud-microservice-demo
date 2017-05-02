package com.tony.demo.microservice.mud.web.manager.service.downstream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "mud-microservice-user", fallback = RemoteUserService.RemoteUserServiceFallback.class)
public interface RemoteUserService {

    @GetMapping("/user/getByLoginName")
    Map<String, Object> getByLoginName(@RequestParam("loginName") String loginName);

    @Component
    class RemoteUserServiceFallback implements RemoteUserService {

        private Logger logger = LoggerFactory.getLogger(RemoteUserServiceFallback.class);

        @Override
        public Map<String, Object> getByLoginName(String loginName) {
            logger.warn("Failed to invoke remote service of mud-microservice-user.");
            return null;
        }
    }
}
