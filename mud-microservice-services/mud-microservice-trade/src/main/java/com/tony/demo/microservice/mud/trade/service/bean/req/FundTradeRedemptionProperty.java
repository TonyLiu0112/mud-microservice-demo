package com.tony.demo.microservice.mud.trade.service.bean.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * 组合赎回基金属性
 * <p>
 * <p>
 * Created by Tony on 14/06/2017.
 */
@ApiModel(description = "赎回基金明细")
public class FundTradeRedemptionProperty {

    /**
     * 基金代码
     */
    @ApiModelProperty(required = true, notes = "基金代码")
    private String fundCode;

    /**
     * 基金名称
     */
    @ApiModelProperty(required = true, notes = "基金名称")
    private String fundName;

    /**
     * 基金赎回份额
     */
    @ApiModelProperty(required = true, notes = "基金赎回份额")
    private BigDecimal share;

    /**
     * 当前基金赎回状态
     * 1. 赎回申请中
     * 2. 赎回申请成功
     * 3. 赎回申请失败
     */
    private Integer status;

    public FundTradeRedemptionProperty() {
    }

    public FundTradeRedemptionProperty(String fundCode, String fundName, BigDecimal share) {
        this.fundCode = fundCode;
        this.fundName = fundName;
        this.share = share;
    }

    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public BigDecimal getShare() {
        return share;
    }

    public void setShare(BigDecimal share) {
        this.share = share;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("基金名称: %s, 基金代码: %s, 赎回份额: %s", fundName, fundCode, share);
    }
}
