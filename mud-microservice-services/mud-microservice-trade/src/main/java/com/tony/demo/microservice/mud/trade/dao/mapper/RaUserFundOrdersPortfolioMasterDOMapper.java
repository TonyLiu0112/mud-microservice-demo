package com.tony.demo.microservice.mud.trade.dao.mapper;

import com.tony.demo.microservice.mud.trade.dao.entity.RaUserFundOrdersPortfolioMasterDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RaUserFundOrdersPortfolioMasterDOMapper {
    int insert(RaUserFundOrdersPortfolioMasterDO record);

    int insertSelective(RaUserFundOrdersPortfolioMasterDO record);

    RaUserFundOrdersPortfolioMasterDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RaUserFundOrdersPortfolioMasterDO record);

    int updateByPrimaryKey(RaUserFundOrdersPortfolioMasterDO record);

    List<RaUserFundOrdersPortfolioMasterDO> selectByPortfolioId(@Param("portfolioId") Integer portfolioId, @Param("userId") Integer userId);
}