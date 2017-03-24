package com.tony.demo.microservice.mud.dao.repository;

import com.tony.demo.microservice.mud.dao.entity.CustomerActivityAttrDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for CustomerActivityAttr
 * <p>
 * Created by Tony on 09/02/2017.
 */
public interface CustomerActivityAttrRepository extends JpaRepository<CustomerActivityAttrDO, Long> {

    List<CustomerActivityAttrDO> findByCustomerActivityIdAndDf(long customerActivityId, int df);

    Page<CustomerActivityAttrDO> findByDfAndCustomerActivityId(int df, long customerActivityId, Pageable pageRequest);

}
