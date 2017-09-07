package com.tony.demo.microservice.mud.services.shoppingcard.dao.mapper;

import com.tony.demo.microservice.mud.services.shoppingcard.dao.entity.ShoppingcardDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShoppingcardDOMapper {

    int insert(ShoppingcardDO record);

    int insertSelective(ShoppingcardDO record);

    ShoppingcardDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ShoppingcardDO record);

    int updateByPrimaryKey(ShoppingcardDO record);
}