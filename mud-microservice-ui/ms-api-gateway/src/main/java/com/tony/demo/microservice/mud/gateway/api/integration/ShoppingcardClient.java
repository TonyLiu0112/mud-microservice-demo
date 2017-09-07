package com.tony.demo.microservice.mud.gateway.api.integration;

import com.wrench.utils.restfulapi.response.RestfulBuilder;
import com.wrench.utils.restfulapi.response.RestfulResponse;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.tony.demo.microservice.mud.gateway.api.integration.content.Instances.SHOPPINGCARD;

@FeignClient(name = SHOPPINGCARD, fallbackFactory = ShoppingcardClient.ShoppingcardFallbackFactory.class)
public interface ShoppingcardClient {

    @GetMapping("sc/shoppingcards")
    ResponseEntity<RestfulResponse> getShoppingcard(@RequestParam("userId") Long userId);

    @Component
    class ShoppingcardFallbackFactory implements FallbackFactory<ShoppingcardClient> {

        private Logger logger = LoggerFactory.getLogger(ShoppingcardFallbackFactory.class);

        @Override
        public ShoppingcardClient create(Throwable cause) {
            return userId -> {
                logger.error("调用[{}]服务异常, ", SHOPPINGCARD, cause);
                return RestfulBuilder.serverError();
            };
        }
    }


}
