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

import static com.tony.demo.microservice.mud.gateway.api.integration.content.Instances.REVIEW;

@FeignClient(name = REVIEW, fallbackFactory = ReviewClient.ReviewFallbackFactory.class)
public interface ReviewClient {

    @GetMapping("review/reviews")
    ResponseEntity<RestfulResponse> getReviews(@RequestParam("productId") Long productId, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum);

    @Component
    class ReviewFallbackFactory implements FallbackFactory<ReviewClient> {

        private Logger logger = LoggerFactory.getLogger(ReviewFallbackFactory.class);

        @Override
        public ReviewClient create(final Throwable cause) {
            return (productId, pageNum) -> {
                logger.error("调用[Review]服务异常,", cause);
                return RestfulBuilder.serverError();
            };
        }
    }

}
