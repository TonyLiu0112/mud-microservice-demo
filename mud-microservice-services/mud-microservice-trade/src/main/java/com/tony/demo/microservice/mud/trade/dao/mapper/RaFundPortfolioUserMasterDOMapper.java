package com.tony.demo.microservice.mud.trade.dao.mapper;

import com.tony.demo.microservice.mud.trade.dao.entity.RaFundPortfolioUserMasterDO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface RaFundPortfolioUserMasterDOMapper {
    int insert(RaFundPortfolioUserMasterDO record);

    int insertSelective(RaFundPortfolioUserMasterDO record);

    RaFundPortfolioUserMasterDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RaFundPortfolioUserMasterDO record);

    int updateByPrimaryKey(RaFundPortfolioUserMasterDO record);

    RaFundPortfolioUserMasterDO selectByPortfolioIdAndUserId(@Param("portfolioId") Integer portfolioId,
                                                             @Param("userId") Integer userId);

    BigDecimal getTotalProfitAndLoss(RaFundPortfolioUserMasterDO rfpum);

    List<RaFundPortfolioUserMasterDO> selectByUserId(@Param("userId") Integer userId);

    BigDecimal queryAccumulatedProfit(@Param("userId") Integer userId);

}