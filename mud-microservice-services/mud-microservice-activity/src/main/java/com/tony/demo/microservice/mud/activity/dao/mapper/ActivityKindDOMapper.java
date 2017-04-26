package com.tony.demo.microservice.mud.activity.dao.mapper;

import com.tony.demo.microservice.mud.activity.dao.entity.ActivityKindDO;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityKindDOMapper {
    int insert(ActivityKindDO record);

    int insertSelective(ActivityKindDO record);

    ActivityKindDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ActivityKindDO record);

    int updateByPrimaryKey(ActivityKindDO record);
}