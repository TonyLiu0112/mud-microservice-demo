package com.tony.demo.microservice.mud.dao.mapper;

import com.tony.demo.microservice.mud.dao.entity.CustomerActivityScoreAttrDO;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerActivityScoreAttrDOMapper {
    int insert(CustomerActivityScoreAttrDO record);

    int insertSelective(CustomerActivityScoreAttrDO record);

    CustomerActivityScoreAttrDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerActivityScoreAttrDO record);

    int updateByPrimaryKey(CustomerActivityScoreAttrDO record);
}