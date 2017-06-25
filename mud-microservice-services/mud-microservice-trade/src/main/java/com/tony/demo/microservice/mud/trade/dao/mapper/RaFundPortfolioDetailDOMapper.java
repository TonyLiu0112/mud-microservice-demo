package com.tony.demo.microservice.mud.trade.dao.mapper;

import com.tony.demo.microservice.mud.trade.dao.entity.RaFundPortfolioDetailDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RaFundPortfolioDetailDOMapper {
    int insert(RaFundPortfolioDetailDO record);

    int insertSelective(RaFundPortfolioDetailDO record);

    RaFundPortfolioDetailDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RaFundPortfolioDetailDO record);

    int updateByPrimaryKey(RaFundPortfolioDetailDO record);

    List<RaFundPortfolioDetailDO> selectByPortfolioId(@Param("portfolioId") Integer portfolioId);

    RaFundPortfolioDetailDO selectByPortfolioIdAndFundCode(@Param("portfolioId") Integer portfolioId,
                                                           @Param("fundCode") String fundCode);
}