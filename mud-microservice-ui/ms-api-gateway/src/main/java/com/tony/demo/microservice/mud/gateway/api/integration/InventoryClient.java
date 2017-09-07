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
import org.springframework.web.bind.annotation.PathVariable;

import static com.tony.demo.microservice.mud.gateway.api.integration.content.Instances.INVENTORY;

@FeignClient(name = INVENTORY, fallbackFactory = InventoryClient.InventoryFallbackFactory.class)
public interface InventoryClient {

    @GetMapping("inventory/query/inventories/product/{pId}")
    ResponseEntity<RestfulResponse> getInventory(@PathVariable("pId") Long productId);

    @Component
    class InventoryFallbackFactory implements FallbackFactory<InventoryClient> {

        private Logger logger = LoggerFactory.getLogger(InventoryFallbackFactory.class);

        @Override
        public InventoryClient create(Throwable cause) {
            return productId -> {
                logger.error("调用[{}]服务异常, ", INVENTORY, cause);
                return RestfulBuilder.serverError();
            };
        }
    }

}
