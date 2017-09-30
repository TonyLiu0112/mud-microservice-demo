package com.tony.demo.microservice.mud.services.shoppingcard.integration;

import com.tony.demo.microservice.mud.services.shoppingcard.service.bean.response.ProductRes;
import com.wrench.utils.restfulapi.response.RestfulBuilder;
import com.wrench.utils.restfulapi.response.RestfulResponse;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-product", fallbackFactory = ProductClient.ProductFallbackFactory.class)
public interface ProductClient {

    @GetMapping("product/products/{id}")
    ResponseEntity<RestfulResponse<ProductRes>> getProduct(@PathVariable("id") Long id);

    @Component
    class ProductFallbackFactory implements FallbackFactory<ProductClient> {

        private Logger logger = LoggerFactory.getLogger(ProductFallbackFactory.class);

        @Override
        public ProductClient create(Throwable cause) {
            return id -> {
                logger.error("调用[{}]服务异常,", "ms-product", cause);
                return RestfulBuilder.serverError4Fallback();
            };
        }
    }

}
