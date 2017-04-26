package com.tony.demo.microservice.mud.customer.dao.mapper;

import com.tony.demo.microservice.mud.customer.dao.entity.CustomerActivityScoreDO;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerActivityScoreDOMapper {
    int insert(CustomerActivityScoreDO record);

    int insertSelective(CustomerActivityScoreDO record);

    CustomerActivityScoreDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerActivityScoreDO record);

    int updateByPrimaryKey(CustomerActivityScoreDO record);
}