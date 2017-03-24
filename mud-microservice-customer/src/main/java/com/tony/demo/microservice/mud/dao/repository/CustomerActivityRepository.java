package com.tony.demo.microservice.mud.dao.repository;

import com.tony.demo.microservice.mud.dao.entity.CustomerActivityDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for CustomerActivityEntity
 * <p>
 * Created by Tony on 09/02/2017.
 */
public interface CustomerActivityRepository extends JpaRepository<CustomerActivityDO, Long> {

    Page<CustomerActivityDO> findByDf(int df, Pageable pageable);

    List<CustomerActivityDO> findByDf(int df);
}
