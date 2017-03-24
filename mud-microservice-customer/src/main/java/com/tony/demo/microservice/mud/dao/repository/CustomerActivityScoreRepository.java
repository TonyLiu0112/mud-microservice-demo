package com.tony.demo.microservice.mud.dao.repository;

import com.tony.demo.microservice.mud.dao.entity.CustomerActivityScoreDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for CustomerActivityScoreEntity
 * <p>
 * Created by Tony on 09/02/2017.
 */
public interface CustomerActivityScoreRepository extends JpaRepository<CustomerActivityScoreDO, Long> {
}
