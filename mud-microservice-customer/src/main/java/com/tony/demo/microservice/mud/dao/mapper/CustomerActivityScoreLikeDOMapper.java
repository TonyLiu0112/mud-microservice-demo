package com.tony.demo.microservice.mud.dao.mapper;

import com.tony.demo.microservice.mud.dao.entity.CustomerActivityScoreLikeDO;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerActivityScoreLikeDOMapper {
    int insert(CustomerActivityScoreLikeDO record);

    int insertSelective(CustomerActivityScoreLikeDO record);

    CustomerActivityScoreLikeDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerActivityScoreLikeDO record);

    int updateByPrimaryKey(CustomerActivityScoreLikeDO record);
}