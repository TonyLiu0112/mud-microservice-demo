package com.tony.demo.microservice.mud.activity.dao.repository;

import com.tony.demo.microservice.mud.activity.dao.entity.ActivityDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for ACTIVITY
 * <p>
 * Created by Tony on 07/02/2017.
 */
@Repository
public interface ActivityRepository extends JpaRepository<ActivityDO, Long> {

    List<ActivityDO> findByNameAndDf(String name, Integer df);

    Page<ActivityDO> findByDf(int df, Pageable pageable);

    List<ActivityDO> findByDf(int df);

}
