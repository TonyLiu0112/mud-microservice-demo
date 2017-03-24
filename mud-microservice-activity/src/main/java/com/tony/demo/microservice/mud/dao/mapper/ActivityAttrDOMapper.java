package com.tony.demo.microservice.mud.dao.mapper;

import com.tony.demo.microservice.mud.dao.entity.ActivityAttrDO;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityAttrDOMapper {
    int insert(ActivityAttrDO record);

    int insertSelective(ActivityAttrDO record);

    ActivityAttrDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ActivityAttrDO record);

    int updateByPrimaryKey(ActivityAttrDO record);
}