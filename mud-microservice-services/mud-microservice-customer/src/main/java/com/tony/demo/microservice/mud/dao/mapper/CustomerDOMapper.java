package com.tony.demo.microservice.mud.dao.mapper;

import com.tony.demo.microservice.mud.dao.entity.CustomerDO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
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