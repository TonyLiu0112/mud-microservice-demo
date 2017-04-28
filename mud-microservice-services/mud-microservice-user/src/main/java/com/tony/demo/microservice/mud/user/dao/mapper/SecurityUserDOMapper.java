package com.tony.demo.microservice.mud.user.dao.mapper;

import com.tony.demo.microservice.mud.user.dao.entity.SecurityUserDO;

public interface SecurityUserDOMapper {
    int insert(SecurityUserDO record);

    int insertSelective(SecurityUserDO record);

    SecurityUserDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SecurityUserDO record);

    int updateByPrimaryKey(SecurityUserDO record);
}