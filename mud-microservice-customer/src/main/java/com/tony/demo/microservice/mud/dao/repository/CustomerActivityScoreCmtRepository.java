package com.tony.demo.microservice.mud.dao.repository;

import com.tony.demo.microservice.mud.dao.entity.CustomerActivityScoreCommentDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for CustomerActivityScoreCmtEntity
 * <p>
 * Created by Tony on 09/02/2017.
 */
public interface CustomerActivityScoreCmtRepository extends JpaRepository<CustomerActivityScoreCommentDO, Long> {
}
