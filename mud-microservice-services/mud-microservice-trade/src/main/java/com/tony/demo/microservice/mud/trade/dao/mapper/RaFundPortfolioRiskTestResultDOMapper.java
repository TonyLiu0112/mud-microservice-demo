package com.tony.demo.microservice.mud.trade.dao.mapper;

import com.tony.demo.microservice.mud.trade.dao.entity.RaFundPortfolioRiskTestResultDO;

public interface RaFundPortfolioRiskTestResultDOMapper {
    int insert(RaFundPortfolioRiskTestResultDO record);

    int insertSelective(RaFundPortfolioRiskTestResultDO record);

    RaFundPortfolioRiskTestResultDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RaFundPortfolioRiskTestResultDO record);

    int updateByPrimaryKey(RaFundPortfolioRiskTestResultDO record);

    RaFundPortfolioRiskTestResultDO queryRiskTestResult(int userId);

    int queryMaxId();

    int deleteByUserId(int userId);
}