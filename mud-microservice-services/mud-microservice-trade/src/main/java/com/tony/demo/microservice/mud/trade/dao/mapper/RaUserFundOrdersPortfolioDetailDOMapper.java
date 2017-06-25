package com.tony.demo.microservice.mud.trade.dao.mapper;

import com.tony.demo.microservice.mud.trade.dao.entity.RaUserFundOrdersPortfolioDetailDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RaUserFundOrdersPortfolioDetailDOMapper {
    int insert(RaUserFundOrdersPortfolioDetailDO record);

    int insertSelective(RaUserFundOrdersPortfolioDetailDO record);

    RaUserFundOrdersPortfolioDetailDO selectByPrimaryKey(@Param("orderId") String orderId, @Param("fundCode") String fundCode);

    int updateByPrimaryKeySelective(RaUserFundOrdersPortfolioDetailDO record);

    int updateByPrimaryKey(RaUserFundOrdersPortfolioDetailDO record);

}