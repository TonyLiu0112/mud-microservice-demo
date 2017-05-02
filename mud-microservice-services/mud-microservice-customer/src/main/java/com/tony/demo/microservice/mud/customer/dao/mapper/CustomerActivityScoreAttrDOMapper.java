package com.tony.demo.microservice.mud.customer.dao.mapper;

import com.tony.demo.microservice.mud.customer.dao.entity.CustomerActivityScoreAttrDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerActivityScoreAttrDOMapper {
    int insert(CustomerActivityScoreAttrDO record);

    int insertSelective(CustomerActivityScoreAttrDO record);

    CustomerActivityScoreAttrDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerActivityScoreAttrDO record);

    int updateByPrimaryKey(CustomerActivityScoreAttrDO record);
}