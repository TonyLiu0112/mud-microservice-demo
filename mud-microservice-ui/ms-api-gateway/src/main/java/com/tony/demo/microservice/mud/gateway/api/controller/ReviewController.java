package com.tony.demo.microservice.mud.gateway.api.controller;

import com.tony.demo.microservice.mud.gateway.api.service.ReviewService;
import com.tony.demo.microservice.mud.gateway.api.service.response.ReviewPageRes;
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
public class ReviewController extends BaseController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
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
            Optional<ReviewPageRes> reviews = reviewService.getReviews(productId, pageNum);
            if (reviews.isPresent())
                return RestfulBuilder.ok(reviews.get());
            return RestfulBuilder.notFound();
        } catch (Exception e) {
            return RestfulBuilder.serverError();
        }
    }

}
