package com.tony.demo.microservice.mud.customer.dao.mapper;

import com.tony.demo.microservice.mud.customer.dao.entity.CustomerActivityScoreLikeDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerActivityScoreLikeDOMapper {
    int insert(CustomerActivityScoreLikeDO record);

    int insertSelective(CustomerActivityScoreLikeDO record);

    CustomerActivityScoreLikeDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerActivityScoreLikeDO record);

    int updateByPrimaryKey(CustomerActivityScoreLikeDO record);
}