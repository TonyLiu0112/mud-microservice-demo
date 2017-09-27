package com.tony.demo.microservice.mud.gateway.api.integration;

import com.tony.demo.microservice.mud.gateway.api.service.request.ShoppingcardReq;
import com.tony.demo.microservice.mud.gateway.api.service.response.ShoppingcardRes;
import com.wrench.utils.restfulapi.response.RestfulBuilder;
import com.wrench.utils.restfulapi.response.RestfulResponse;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.tony.demo.microservice.mud.gateway.api.integration.content.Instances.SHOPPINGCARD;

@FeignClient(name = SHOPPINGCARD, fallbackFactory = ShoppingcardClient.ShoppingcardFallbackFactory.class)
public interface ShoppingcardClient {

    @GetMapping("sc/shoppingcards")
    ResponseEntity<RestfulResponse<List<ShoppingcardRes>>> getShoppingcard(@RequestParam("userId") Long userId);

    @PostMapping("sc/shoppingcards")
    ResponseEntity<RestfulResponse<Long>> storeShoppingcard(@RequestBody ShoppingcardReq shoppingcardReq);

    @Component
    class ShoppingcardFallbackFactory implements FallbackFactory<ShoppingcardClient> {

        private Logger logger = LoggerFactory.getLogger(ShoppingcardFallbackFactory.class);

        @Override
        public ShoppingcardClient create(Throwable cause) {
            return new ShoppingcardClient() {
                @Override
                public ResponseEntity<RestfulResponse<List<ShoppingcardRes>>> getShoppingcard(Long userId) {
                    logger.error("[{}]服务调用失败, 获取购物车列表异常.", SHOPPINGCARD, cause);
                    return RestfulBuilder.serverError4Fallback();
                }

                @Override
                public ResponseEntity<RestfulResponse<Long>> storeShoppingcard(ShoppingcardReq shoppingcardReq) {
                    logger.error("[{}]服务调用失败, 存储购物车异常.", SHOPPINGCARD, cause);
                    return RestfulBuilder.serverError4Fallback();
                }
            };
        }
    }


}
