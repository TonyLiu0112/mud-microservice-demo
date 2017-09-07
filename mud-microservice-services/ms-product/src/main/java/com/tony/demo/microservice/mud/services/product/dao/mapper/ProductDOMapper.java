package com.tony.demo.microservice.mud.services.product.dao.mapper;

import com.tony.demo.microservice.mud.services.product.dao.entity.ProductDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductDOMapper {
    int insert(ProductDO record);

    int insertSelective(ProductDO record);

    ProductDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductDO record);

    int updateByPrimaryKey(ProductDO record);
}