package com.tony.demo.microservice.mud.trade.dao.mapper;

import com.tony.demo.microservice.mud.trade.dao.entity.RaFundPortfolioMasterDO;

public interface RaFundPortfolioMasterDOMapper {
    int insert(RaFundPortfolioMasterDO record);

    int insertSelective(RaFundPortfolioMasterDO record);

    RaFundPortfolioMasterDO selectByPrimaryKey(Integer portfolioId);

    int updateByPrimaryKeySelective(RaFundPortfolioMasterDO record);

    int updateByPrimaryKey(RaFundPortfolioMasterDO record);
}