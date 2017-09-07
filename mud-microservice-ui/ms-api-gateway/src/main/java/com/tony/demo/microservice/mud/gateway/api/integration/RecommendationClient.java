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

import static com.tony.demo.microservice.mud.gateway.api.integration.content.Instances.RECOMMENDATION;

@FeignClient(name = RECOMMENDATION, fallbackFactory = RecommendationClient.RecommendationFallbackFactory.class)
public interface RecommendationClient {

    @GetMapping("recommendation/product/recommendations")
    ResponseEntity<RestfulResponse> recommendations(@RequestParam("tag") String tag);

    @Component
    class RecommendationFallbackFactory implements FallbackFactory<RecommendationClient> {

        private Logger logger = LoggerFactory.getLogger(RecommendationFallbackFactory.class);

        @Override
        public RecommendationClient create(Throwable cause) {
            return tag -> {
                logger.error("调用[Review]服务异常, ", cause);
                return RestfulBuilder.serverError();
            };
        }
    }

}
