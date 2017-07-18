package com.tony.demo.microservice.mud.trade.service.trade;

import com.tony.demo.microservice.mud.trade.service.bean.req.FundProportionRequest;
import com.tony.demo.microservice.mud.trade.service.bean.res.FundPortfolioUserDetailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 组合基金占比计算服务
 * <p>
 * Created by Tony on 06/06/2017.
 */
@Service
public class FundProportionService {

    private static final BigDecimal SCALE = new BigDecimal(100);

    @Autowired
    private FundPortfolioUserDetailService fundPortfolioUserDetailService;

    /**
     * 计算基金份额所占比例
     *
     * @param fundProportionRequest
     * @return
     */
    public Map<String, BigDecimal> calculation(FundProportionRequest fundProportionRequest) {
        Map<String, BigDecimal> results = new HashMap<>();
        BigDecimal amount = fundProportionRequest.getAmount();
        for (Map.Entry<String, BigDecimal> entry : fundProportionRequest.getProportions().entrySet()) {
            BigDecimal proportion = entry.getValue().divide(SCALE, 2, BigDecimal.ROUND_HALF_UP);
            BigDecimal crtAmount = amount.multiply(proportion);
            results.put(entry.getKey(), crtAmount);
        }
        return results;
    }

    /**
     * 根据比例计算实际用户份额
     *
     * @param proportion  占比 一个0-1的小数
     * @param userId      用户id
     * @param portfolioId 组合id
     * @return 各个基金的具体份额占比集合，k：基金代码，v：基金份额比例计算值
     * @throws Exception
     */
    public Map<String, BigDecimal> computerShare(BigDecimal proportion, Integer userId, Integer portfolioId) throws Exception {
        Map<String, BigDecimal> results = new HashMap<>();
        List<FundPortfolioUserDetailResponse> records = fundPortfolioUserDetailService.findByUserIdAndPortfolioId(userId, portfolioId);
        for (FundPortfolioUserDetailResponse record : records) {
            if (record.getShare() != null) {
                record.getShare().multiply(proportion);
                results.put(record.getFundCode(), record.getShare().multiply(proportion));
            }
        }
        return results;
    }

}
