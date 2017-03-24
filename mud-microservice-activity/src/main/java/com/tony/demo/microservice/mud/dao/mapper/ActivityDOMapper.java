package com.tony.demo.microservice.mud.dao.mapper;

import com.tony.demo.microservice.mud.dao.entity.ActivityDO;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityDOMapper {
    int insert(ActivityDO record);

    int insertSelective(ActivityDO record);

    ActivityDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ActivityDO record);

    int updateByPrimaryKey(ActivityDO record);
}