package com.tony.demo.microservice.mud.trade.dao.mapper;

import com.tony.demo.microservice.mud.trade.dao.entity.RaFundPortfolioUserHistoryDO;
import org.apache.ibatis.annotations.Param;

public interface RaFundPortfolioUserHistoryDOMapper {
    int insert(RaFundPortfolioUserHistoryDO record);

    int insertSelective(RaFundPortfolioUserHistoryDO record);

    RaFundPortfolioUserHistoryDO selectByPrimaryKey(@Param("userId") Integer userId, @Param("portfolioId") Integer portfolioId, @Param("recordeDate") Integer recordeDate);

    int updateByPrimaryKeySelective(RaFundPortfolioUserHistoryDO record);

    int updateByPrimaryKey(RaFundPortfolioUserHistoryDO record);
}