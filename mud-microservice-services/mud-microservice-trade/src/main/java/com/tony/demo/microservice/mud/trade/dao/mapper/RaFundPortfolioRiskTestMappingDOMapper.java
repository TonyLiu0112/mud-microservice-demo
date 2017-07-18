package com.tony.demo.microservice.mud.trade.dao.mapper;

import com.tony.demo.microservice.mud.trade.dao.entity.RaFundPortfolioRiskTestMappingDO;

public interface RaFundPortfolioRiskTestMappingDOMapper {
    int insert(RaFundPortfolioRiskTestMappingDO record);

    int insertSelective(RaFundPortfolioRiskTestMappingDO record);

    RaFundPortfolioRiskTestMappingDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RaFundPortfolioRiskTestMappingDO record);

    int updateByPrimaryKey(RaFundPortfolioRiskTestMappingDO record);

    int getRiskPrefer(int score);
}