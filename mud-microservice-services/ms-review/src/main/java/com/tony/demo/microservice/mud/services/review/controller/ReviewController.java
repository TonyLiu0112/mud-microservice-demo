package com.tony.demo.microservice.mud.services.review.controller;

import com.tony.demo.microservice.mud.services.review.service.ReviewService;
import com.tony.demo.microservice.mud.services.review.service.bean.response.ReviewRes;
import com.wrench.utils.restfulapi.response.RestfulBuilder;
import com.wrench.utils.restfulapi.response.RestfulResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static com.wrench.utils.restfulapi.response.RestfulBuilder.*;

@RestController
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("reviews")
    public ResponseEntity<RestfulResponse> getReviews(@RequestParam("productId") Long productId) {
        try {
            Optional<List<ReviewRes>> optional = reviewService.getReviews(productId);
            if (optional.isPresent())
                return ok(optional.get());
            return notFound();
        } catch (Exception e) {
            return serverError();
        }
    }

}
