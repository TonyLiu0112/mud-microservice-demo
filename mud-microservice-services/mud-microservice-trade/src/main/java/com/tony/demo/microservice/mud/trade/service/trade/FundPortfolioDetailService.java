package com.tony.demo.microservice.mud.trade.service.trade;

import com.tony.demo.microservice.mud.common.utils.ConvertUtils;
import com.tony.demo.microservice.mud.trade.dao.entity.RaFundPortfolioDetailDO;
import com.tony.demo.microservice.mud.trade.dao.mapper.RaFundPortfolioDetailDOMapper;
import com.tony.demo.microservice.mud.trade.service.bean.req.Product;
import com.tony.demo.microservice.mud.trade.service.bean.res.RaFundPortfolioDetailResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.math.BigDecimal.ROUND_HALF_UP;

/**
 * 组合基金配置明细服务
 * <p>
 * Created by Tony on 06/06/2017.
 */
@Service
public class FundPortfolioDetailService {

    private Logger logger = LoggerFactory.getLogger(FundPortfolioDetailService.class);

    private final RaFundPortfolioDetailDOMapper portfolioDetailDOMapper;

    @Autowired
    public FundPortfolioDetailService(RaFundPortfolioDetailDOMapper portfolioDetailDOMapper) {
        this.portfolioDetailDOMapper = portfolioDetailDOMapper;
    }

    /**
     * 根据组合id获取组合配置明细列表
     *
     * @param portfolioId
     * @return
     * @throws Exception
     */
    public List<RaFundPortfolioDetailResponse> findByPortfolioId(int portfolioId) throws Exception {
        if (portfolioId <= 0)
            return new ArrayList<>();
        List<RaFundPortfolioDetailDO> records = portfolioDetailDOMapper.selectByPortfolioId(portfolioId);
        if (records == null || records.size() == 0)
            return new ArrayList<>();
        return ConvertUtils.convert(records, RaFundPortfolioDetailResponse.class);
    }

    /**
     * 计算组合最低申购金额
     *
     * @param fundDetails
     * @return
     */
    public BigDecimal getMiniAmount(List<RaFundPortfolioDetailResponse> fundDetails) {
        BigDecimal lowestAmount = BigDecimal.ZERO;
        if (fundDetails == null || fundDetails.size() == 0)
            return lowestAmount;
        for (RaFundPortfolioDetailResponse fund : fundDetails) {
            Product product = new Product();
            BigDecimal fundMiniAmount = new BigDecimal(product.getLowestAmount());
            BigDecimal proportion = fund.getPercentBegin().divide(new BigDecimal(100), 2, ROUND_HALF_UP);
            BigDecimal crtAmount = fundMiniAmount.divide(proportion, 2, ROUND_HALF_UP);
            if (lowestAmount.compareTo(crtAmount) < 0)
                lowestAmount = crtAmount;
        }
        return lowestAmount;
    }

    public BigDecimal getMiniAmount(int portfolioId) throws Exception {
        return this.getMiniAmount(findByPortfolioId(portfolioId));
    }

    public RaFundPortfolioDetailResponse findFundDetail(Integer portfolioId, String fundCode) {
        RaFundPortfolioDetailResponse response = new RaFundPortfolioDetailResponse();
        RaFundPortfolioDetailDO record = portfolioDetailDOMapper.selectByPortfolioIdAndFundCode(portfolioId, fundCode);
        if (record != null)
            BeanUtils.copyProperties(record, response);
        return response;
    }
}
