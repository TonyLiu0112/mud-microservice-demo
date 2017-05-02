package com.tony.demo.microservice.mud.activity.dao.mapper;

import com.tony.demo.microservice.mud.activity.dao.entity.ActivityAttrDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ActivityAttrDOMapper {
    int insert(ActivityAttrDO record);

    int insertSelective(ActivityAttrDO record);

    ActivityAttrDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ActivityAttrDO record);

    int updateByPrimaryKey(ActivityAttrDO record);
}