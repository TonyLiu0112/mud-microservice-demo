package com.tony.demo.microservice.mud.trade.dao.mapper;

import com.tony.demo.microservice.mud.trade.dao.entity.RaFundPortfolioRiskMatrixDO;

public interface RaFundPortfolioRiskMatrixDOMapper {
    int insert(RaFundPortfolioRiskMatrixDO record);

    int insertSelective(RaFundPortfolioRiskMatrixDO record);

    RaFundPortfolioRiskMatrixDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RaFundPortfolioRiskMatrixDO record);

    int updateByPrimaryKey(RaFundPortfolioRiskMatrixDO record);
}