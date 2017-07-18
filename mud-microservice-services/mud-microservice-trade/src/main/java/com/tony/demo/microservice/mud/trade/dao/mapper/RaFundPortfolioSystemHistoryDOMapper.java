package com.tony.demo.microservice.mud.trade.dao.mapper;

import com.tony.demo.microservice.mud.trade.dao.entity.RaFundPortfolioSystemHistoryDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RaFundPortfolioSystemHistoryDOMapper {
    int insert(RaFundPortfolioSystemHistoryDO record);

    int insertSelective(RaFundPortfolioSystemHistoryDO record);

    RaFundPortfolioSystemHistoryDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RaFundPortfolioSystemHistoryDO record);

    int updateByPrimaryKey(RaFundPortfolioSystemHistoryDO record);

    List<RaFundPortfolioSystemHistoryDO> selectByPortfolioIdAndDate(@Param("portfolioId") Integer portfolioId, @Param("date") Integer date);
}