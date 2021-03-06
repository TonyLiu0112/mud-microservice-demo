package com.tony.demo.microservice.mud.customer.dao.repository;

import com.tony.demo.microservice.mud.customer.dao.entity.CustomerActivityScoreAttrDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for CustomerActivityScoreAttrEntity
 * <p>
 * Created by Tony on 09/02/2017.
 */
@Repository
public interface CustomerActivityScoreAttrRepository extends JpaRepository<CustomerActivityScoreAttrDO, Long> {

    List<CustomerActivityScoreAttrDO> findByCustomerActivityIdAndDf(long customerActivityId, int df);
    
    Page<CustomerActivityScoreAttrDO> findByDfAndCustomerActivityId(int df, long customerActivityId, Pageable pageRequest);

}
