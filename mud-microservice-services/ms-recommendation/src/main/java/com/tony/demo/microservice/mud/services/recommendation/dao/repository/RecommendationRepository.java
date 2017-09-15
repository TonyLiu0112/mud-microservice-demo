package com.tony.demo.microservice.mud.services.recommendation.dao.repository;

import com.tony.demo.microservice.mud.services.recommendation.dao.entity.RecommendationDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendationRepository extends JpaRepository<RecommendationDO, Long> {
    List<RecommendationDO> findByTagContaining(String tag);
}
