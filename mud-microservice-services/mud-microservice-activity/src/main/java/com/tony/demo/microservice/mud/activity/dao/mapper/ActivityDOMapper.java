package com.tony.demo.microservice.mud.activity.dao.mapper;

import com.tony.demo.microservice.mud.activity.dao.entity.ActivityDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ActivityDOMapper {
    int insert(ActivityDO record);

    int insertSelective(ActivityDO record);

    ActivityDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ActivityDO record);

    int updateByPrimaryKey(ActivityDO record);

	ActivityDO findByName(@Param("name") String name);
}