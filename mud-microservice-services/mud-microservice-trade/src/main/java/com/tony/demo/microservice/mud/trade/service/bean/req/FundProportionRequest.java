package com.tony.demo.microservice.mud.trade.service.bean.req;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 基金占比计算请求实体
 * <p>
 * Created by Tony on 06/06/2017.
 */
public class FundProportionRequest {

    /**
     * 总金额
     */
    private BigDecimal amount;

    /**
     * 组合中各个基金占比
     * <br>
     * k: 基金代码
     * v: 所占比例
     */
    private Map<String, BigDecimal> proportions;

    public FundProportionRequest(BigDecimal amount, Map<String, BigDecimal> proportions) {
        this.amount = amount;
        this.proportions = proportions;
    }

    public FundProportionRequest() {
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Map<String, BigDecimal> getProportions() {
        return proportions;
    }

    public void setProportions(Map<String, BigDecimal> proportions) {
        this.proportions = proportions;
    }
}
