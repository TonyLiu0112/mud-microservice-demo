package com.tony.demo.microservice.mud.services.recommendation.controller;

import com.tony.demo.microservice.mud.services.recommendation.service.RecommendationService;
import com.tony.demo.microservice.mud.services.recommendation.service.bean.response.RecommendationRes;
import com.wrench.utils.restfulapi.response.RestfulResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static com.wrench.utils.restfulapi.response.RestfulBuilder.*;

@RestController
@RequestMapping("product")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("recommendations")
    public ResponseEntity<RestfulResponse> recommendations(@RequestParam("tag") String tag) {
        try {
            Optional<List<RecommendationRes>> optional = recommendationService.getRecommendations(tag);
            if (optional.isPresent())
                return ok(optional.get());
            return notFound();
        } catch (Exception e) {
            return serverError();
        }
    }

}
