package com.tony.demo.microservice.mud.customer.dao.mapper;

import com.tony.demo.microservice.mud.customer.dao.entity.CustomerActivityAttrDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerActivityAttrDOMapper {
    int insert(CustomerActivityAttrDO record);

    int insertSelective(CustomerActivityAttrDO record);

    CustomerActivityAttrDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerActivityAttrDO record);

    int updateByPrimaryKey(CustomerActivityAttrDO record);
}