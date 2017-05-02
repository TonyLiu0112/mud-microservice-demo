package com.tony.demo.microservice.mud.customer.dao.mapper;

import com.tony.demo.microservice.mud.customer.dao.entity.CustomerDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CustomerDOMapper {
    int insert(CustomerDO record);

    int insertSelective(CustomerDO record);

    CustomerDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerDO record);

    int updateByPrimaryKey(CustomerDO record);

    /**
     * 客户列表查询
     *
     * @return
     * @throws Exception
     */
    List<CustomerDO> selectList() throws Exception;
}