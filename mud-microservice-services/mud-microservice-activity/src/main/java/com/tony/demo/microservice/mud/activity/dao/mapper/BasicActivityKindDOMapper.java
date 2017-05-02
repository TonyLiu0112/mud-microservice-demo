package com.tony.demo.microservice.mud.activity.dao.mapper;

import com.tony.demo.microservice.mud.activity.dao.entity.BasicActivityKindDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BasicActivityKindDOMapper {
    int insert(BasicActivityKindDO record);

    int insertSelective(BasicActivityKindDO record);

    BasicActivityKindDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BasicActivityKindDO record);

    int updateByPrimaryKey(BasicActivityKindDO record);
}