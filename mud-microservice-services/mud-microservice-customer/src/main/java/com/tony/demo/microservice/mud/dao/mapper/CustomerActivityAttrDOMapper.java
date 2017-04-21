package com.tony.demo.microservice.mud.dao.mapper;

import com.tony.demo.microservice.mud.dao.entity.CustomerActivityAttrDO;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerActivityAttrDOMapper {
    int insert(CustomerActivityAttrDO record);

    int insertSelective(CustomerActivityAttrDO record);

    CustomerActivityAttrDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerActivityAttrDO record);

    int updateByPrimaryKey(CustomerActivityAttrDO record);
}