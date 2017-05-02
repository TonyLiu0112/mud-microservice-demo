package com.tony.demo.microservice.mud.customer.dao.mapper;

import com.tony.demo.microservice.mud.customer.dao.entity.CustomerActivityDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerActivityDOMapper {
    int insert(CustomerActivityDO record);

    int insertSelective(CustomerActivityDO record);

    CustomerActivityDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerActivityDO record);

    int updateByPrimaryKey(CustomerActivityDO record);
}