package com.tony.demo.microservice.mud.services.review.dao.repository;

import com.tony.demo.microservice.mud.services.review.dao.entity.ReviewDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewDO, Long> {
    Page<ReviewDO> findByProductId(long productId, Pageable pageRequest);
}
