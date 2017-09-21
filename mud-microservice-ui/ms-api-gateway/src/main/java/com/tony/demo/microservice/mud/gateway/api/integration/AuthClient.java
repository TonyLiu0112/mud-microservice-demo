package com.tony.demo.microservice.mud.gateway.api.integration;

import com.tony.demo.microservice.mud.gateway.api.service.request.AuthReq;
import com.tony.demo.microservice.mud.gateway.api.service.response.AuthRes;
import com.wrench.utils.restfulapi.response.RestfulBuilder;
import com.wrench.utils.restfulapi.response.RestfulResponse;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.tony.demo.microservice.mud.gateway.api.integration.content.Instances.AUTH;

@FeignClient(name = AUTH, fallbackFactory = AuthClient.AuthClientFallback.class)
public interface AuthClient {

    @PostMapping("security/doLogin")
    ResponseEntity<RestfulResponse<AuthRes>> getToken(@RequestBody AuthReq authReq);

    @Component
    class AuthClientFallback implements FallbackFactory<AuthClient> {

        private Logger _logger = LoggerFactory.getLogger(AuthClientFallback.class);

        @Override
        public AuthClient create(Throwable cause) {
            _logger.error("Failed to invoke AUTH instance. ", cause);
            return authReq -> RestfulBuilder.serverError4Fallback();
        }
    }

}
