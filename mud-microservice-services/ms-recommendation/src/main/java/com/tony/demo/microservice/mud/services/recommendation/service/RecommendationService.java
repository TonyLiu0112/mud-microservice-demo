package com.tony.demo.microservice.mud.services.recommendation.service;

import com.tony.demo.microservice.mud.common.utils.ConvertUtils;
import com.tony.demo.microservice.mud.services.recommendation.dao.entity.RecommendationDO;
import com.tony.demo.microservice.mud.services.recommendation.dao.repository.RecommendationRepository;
import com.tony.demo.microservice.mud.services.recommendation.service.bean.response.RecommendationRes;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;

    public RecommendationService(RecommendationRepository recommendationRepository) {
        this.recommendationRepository = recommendationRepository;
    }

    public Optional<List<RecommendationRes>> getRecommendations(String tag) throws Exception {
        List<RecommendationDO> records = recommendationRepository.findByTagLike(tag);
        return Optional.of(ConvertUtils.convert(records, RecommendationRes.class));
    }

}
