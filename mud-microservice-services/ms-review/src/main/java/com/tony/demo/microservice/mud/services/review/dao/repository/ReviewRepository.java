package com.tony.demo.microservice.mud.services.review.dao.repository;

import com.tony.demo.microservice.mud.services.review.dao.entity.ReviewDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewDO, Long> {
    List<ReviewDO> findByProductId(long productId);
}
