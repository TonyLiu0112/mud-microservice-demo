package com.tony.demo.microservice.mud.services.inventory.dao.mapper;

import com.tony.demo.microservice.mud.services.inventory.dao.entity.InventoryDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InventoryDOMapper {
    int insert(InventoryDO record);

    int insertSelective(InventoryDO record);

    InventoryDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(InventoryDO record);

    int updateByPrimaryKey(InventoryDO record);
}