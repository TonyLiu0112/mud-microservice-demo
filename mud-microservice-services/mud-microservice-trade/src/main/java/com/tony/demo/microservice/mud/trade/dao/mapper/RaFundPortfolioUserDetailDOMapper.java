package com.tony.demo.microservice.mud.trade.dao.mapper;

import com.tony.demo.microservice.mud.trade.dao.entity.RaFundPortfolioUserDetailDO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface RaFundPortfolioUserDetailDOMapper {
    int insert(RaFundPortfolioUserDetailDO record);

    int insertSelective(RaFundPortfolioUserDetailDO record);

    RaFundPortfolioUserDetailDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RaFundPortfolioUserDetailDO record);

    int updateByPrimaryKey(RaFundPortfolioUserDetailDO record);

    RaFundPortfolioUserDetailDO selectUserDetail(@Param("portfolioId") Integer portfolioId,
                                                 @Param("fundCode") String fundCode,
                                                 @Param("userId") Integer userId);

    List<RaFundPortfolioUserDetailDO> selectByUserIdAndPortfolioId(@Param("userId") Integer userId,
                                                                   @Param("portfolioId") Integer portfolioId);

    List<RaFundPortfolioUserDetailDO> selectUsableUserDetails(@Param("userId") Integer userId,
                                                              @Param("portfolioId") Integer portfolioId);
    BigDecimal getTotalAssetsByEntity(RaFundPortfolioUserDetailDO rfpud);

    List<RaFundPortfolioUserDetailDO> getRedemptionFundListByOrderEntity(RaFundPortfolioUserDetailDO rfpud);

    List<RaFundPortfolioUserDetailDO> getConfirmationFundListByOrderEntity(
            com.tony.demo.microservice.mud.trade.dao.entity.RaUserFundOrdersPortfolioDetailDO rufpd);

    List<RaFundPortfolioUserDetailDO> selectByUserId(@Param("userId") int userId);

    RaFundPortfolioUserDetailDO selectByUserIdFundCode(@Param("userId") int userId,
                                                       @Param("fundCode") String fundCode);
}