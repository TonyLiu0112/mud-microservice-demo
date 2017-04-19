package com.tony.demo.microservice.mud.dao.mapper;

import com.tony.demo.microservice.mud.dao.entity.SecurityUserDO;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityUserDOMapper {
    int insert(SecurityUserDO record);

    int insertSelective(SecurityUserDO record);

    SecurityUserDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SecurityUserDO record);

    int updateByPrimaryKey(SecurityUserDO record);
}