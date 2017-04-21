package com.tony.demo.microservice.mud.dao.mapper;

import com.tony.demo.microservice.mud.dao.entity.CustomerWechatConfigDO;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerWechatConfigDOMapper {
    int insert(CustomerWechatConfigDO record);

    int insertSelective(CustomerWechatConfigDO record);

    CustomerWechatConfigDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerWechatConfigDO record);

    int updateByPrimaryKey(CustomerWechatConfigDO record);
}