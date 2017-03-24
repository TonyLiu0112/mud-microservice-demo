package com.tony.demo.microservice.mud.dao.mapper;

import com.tony.demo.microservice.mud.dao.entity.BasicActivityKindDO;
import org.springframework.stereotype.Repository;

@Repository
public interface BasicActivityKindDOMapper {
    int insert(BasicActivityKindDO record);

    int insertSelective(BasicActivityKindDO record);

    BasicActivityKindDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BasicActivityKindDO record);

    int updateByPrimaryKey(BasicActivityKindDO record);
}