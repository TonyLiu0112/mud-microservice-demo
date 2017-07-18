package com.tony.demo.microservice.mud.trade.dao.mapper;

import com.tony.demo.microservice.mud.trade.dao.entity.RaUserFundOrdersPortfolioDetailDO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface RaUserFundOrdersPortfolioDetailDOMapper {
    int insert(RaUserFundOrdersPortfolioDetailDO record);

    int insertSelective(RaUserFundOrdersPortfolioDetailDO record);

    RaUserFundOrdersPortfolioDetailDO selectByPrimaryKey(@Param("orderId") String orderId, @Param("fundCode") String fundCode);

    int updateByPrimaryKeySelective(RaUserFundOrdersPortfolioDetailDO record);

    int updateByPrimaryKey(RaUserFundOrdersPortfolioDetailDO record);

    List<RaUserFundOrdersPortfolioDetailDO> selectByStatus(@Param("status") int status);

    RaUserFundOrdersPortfolioDetailDO selectByPrimaryOrderId(@Param("orderId") String orderId);

    BigDecimal getTotalAssetsByEntity(RaUserFundOrdersPortfolioDetailDO rufpd);

    List<RaUserFundOrdersPortfolioDetailDO> selectIntermediateByUserId(@Param("userId") Integer userId);

    List<RaUserFundOrdersPortfolioDetailDO> selectTradingGroupFundList(@Param("userId") Integer userId);

    List<RaUserFundOrdersPortfolioDetailDO> selectUserConfirmOrders(@Param("userId") int userId,
                                                                    @Param("portfolioId") int portfolioId,
                                                                    @Param("fundCode") String fundCode);
}