package com.tony.demo.microservice.mud.gateway.api.endpoints;

import com.github.pagehelper.PageInfo;
import com.tony.demo.microservice.mud.gateway.api.service.ReviewService;
import com.tony.demo.microservice.mud.gateway.api.service.response.ReviewRes;
import com.wrench.utils.restfulapi.response.RestfulBuilder;
import com.wrench.utils.restfulapi.response.RestfulResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("additions")
public class ReviewEndpoint extends JwtBaseEndpoint {

    private final ReviewService reviewService;

    public ReviewEndpoint(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    /**
     * 产品评论列表
     *
     * @param productId
     * @param pageNum
     * @return
     */
    @GetMapping("products/reviews")
    public ResponseEntity<RestfulResponse> productReviews(@RequestParam("productId") Long productId,
                                                          @RequestParam("pageNum") Integer pageNum) {
        try {
            Optional<PageInfo<ReviewRes>> reviews = reviewService.getReviews(productId, pageNum);
            if (reviews.isPresent())
                return RestfulBuilder.ok(reviews.get());
            return RestfulBuilder.notFound();
        } catch (Exception e) {
            return RestfulBuilder.serverError();
        }
    }

}
