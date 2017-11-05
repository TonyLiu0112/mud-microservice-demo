package com.tony.demo.microservice.mud.gateway.api.integration;

import com.tony.demo.microservice.mud.gateway.api.service.response.OrderReq;
import com.tony.demo.microservice.mud.gateway.api.service.response.OrderRes;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.tony.demo.microservice.mud.gateway.api.integration.content.Instances.ORDER;

@FeignClient(name = ORDER, fallbackFactory = OrderClient.OrderFallbackFactory.class)
public interface OrderClient {

    @GetMapping("orders")
    ResponseEntity<RestfulResponse<OrderRes>> getOrders();

    @PostMapping("orders")
    ResponseEntity<RestfulResponse> addOrders(@RequestBody OrderReq orderReq);

    @PutMapping("orders/confirm")
    ResponseEntity<RestfulResponse> confirm(@RequestBody OrderReq orderReq);

    @Component
    class OrderFallbackFactory implements FallbackFactory<OrderClient> {

        private Logger logger = LoggerFactory.getLogger(OrderFallbackFactory.class);

        @Override
        public OrderClient create(Throwable cause) {
            return new OrderClient() {
                @Override
                public ResponseEntity<RestfulResponse<OrderRes>> getOrders() {
                    logger.error("Failed to invoke service of ms-order -> getOrders(), error is: {}", cause);
                    return RestfulBuilder.serverError4Fallback();
                }

                @Override
                public ResponseEntity<RestfulResponse> addOrders(OrderReq orderReq) {
                    logger.error("Failed to invoke service of ms-order -> addOrders(), error is: {}", cause);
                    return RestfulBuilder.serverError4Fallback();
                }

                @Override
                public ResponseEntity<RestfulResponse> confirm(OrderReq orderReq) {
                    logger.error("Failed to invoke service of ms-order -> confirm(), error is: {}", cause);
                    return RestfulBuilder.serverError4Fallback();
                }
            };
        }
    }

}
