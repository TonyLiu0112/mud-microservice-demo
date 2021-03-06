package com.tony.demo.microservice.mud.activity.dao.repository;

import com.tony.demo.microservice.mud.activity.dao.entity.ActivityAttrDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for ActivityAttr
 * <p>
 * Created by Tony on 09/02/2017.
 */
@Repository
public interface ActivityAttrRepository extends JpaRepository<ActivityAttrDO, Long> {

    List<ActivityAttrDO> findByActivityId(long activityId);

	Page<ActivityAttrDO> findByDfAndActivityId(int df, long activityId, Pageable pageRequest);

}
