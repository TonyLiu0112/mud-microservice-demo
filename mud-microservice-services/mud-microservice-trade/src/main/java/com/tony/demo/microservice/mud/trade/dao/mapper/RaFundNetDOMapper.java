package com.tony.demo.microservice.mud.trade.dao.mapper;

import com.tony.demo.microservice.mud.trade.dao.entity.RaFundNetDO;
import org.apache.ibatis.annotations.Param;

public interface RaFundNetDOMapper {
    int insert(RaFundNetDO record);

    int insertSelective(RaFundNetDO record);

    RaFundNetDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RaFundNetDO record);

    int updateByPrimaryKey(RaFundNetDO record);

    RaFundNetDO selectLatestByFundCode(@Param("fundCode") String fundCode);
}