package com.tony.demo.microservice.mud.dao.mapper;

import com.tony.demo.microservice.mud.dao.entity.CustomerActivityDO;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerActivityDOMapper {
    int insert(CustomerActivityDO record);

    int insertSelective(CustomerActivityDO record);

    CustomerActivityDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerActivityDO record);

    int updateByPrimaryKey(CustomerActivityDO record);
}