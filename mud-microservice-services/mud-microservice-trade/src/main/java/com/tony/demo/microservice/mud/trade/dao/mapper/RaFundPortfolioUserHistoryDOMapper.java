package com.tony.demo.microservice.mud.trade.dao.mapper;

import com.tony.demo.microservice.mud.trade.dao.entity.RaFundPortfolioUserHistoryDO;
import com.tony.demo.microservice.mud.trade.dao.repository.PortfolioIncomeRO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface RaFundPortfolioUserHistoryDOMapper {
    int insert(RaFundPortfolioUserHistoryDO record);

    int insertSelective(RaFundPortfolioUserHistoryDO record);

    RaFundPortfolioUserHistoryDO selectByPrimaryKey(@Param("userId") Integer userId, @Param("portfolioId") Integer portfolioId, @Param("recordeDate") Integer recordeDate);

    int updateByPrimaryKeySelective(RaFundPortfolioUserHistoryDO record);

    int updateByPrimaryKey(RaFundPortfolioUserHistoryDO record);

    BigDecimal getYesterdayProfitAndLossByEntity(RaFundPortfolioUserHistoryDO rfpuh);

    BigDecimal queryYesterdayProfit(@Param("userId") Integer userId, @Param("recordeDate") Integer recordeDate);

    List<PortfolioIncomeRO> findByPortfolioIdUserId(@Param("portfolioId") Integer portfolioId, @Param("userId") Integer userId);
}