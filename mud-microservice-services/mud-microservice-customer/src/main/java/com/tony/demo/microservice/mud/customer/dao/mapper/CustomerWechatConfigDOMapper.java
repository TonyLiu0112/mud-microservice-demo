package com.tony.demo.microservice.mud.customer.dao.mapper;

import com.tony.demo.microservice.mud.customer.dao.entity.CustomerWechatConfigDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerWechatConfigDOMapper {
    int insert(CustomerWechatConfigDO record);

    int insertSelective(CustomerWechatConfigDO record);

    CustomerWechatConfigDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerWechatConfigDO record);

    int updateByPrimaryKey(CustomerWechatConfigDO record);
}