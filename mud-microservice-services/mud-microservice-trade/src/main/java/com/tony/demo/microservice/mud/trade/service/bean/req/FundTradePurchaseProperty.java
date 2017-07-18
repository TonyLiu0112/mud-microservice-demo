package com.tony.demo.microservice.mud.trade.service.bean.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * 组合申购基金属性
 * <p>
 * Created by Tony on 14/06/2017.
 */
@ApiModel(description = "交易申购各基金属性")
public class FundTradePurchaseProperty {

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
     * 申购基金占比
     * 0-1的小数
     */
    @ApiModelProperty(required = true, notes = "此基金在组合中的占比")
    private BigDecimal proportion;

    /**
     * 申购基金金额
     */
    @ApiModelProperty(required = true, notes = "申购基金的金额")
    private BigDecimal amount;

    @ApiModelProperty(required = true, notes = "排序顺序号")
    private byte sn;

    /**
     * 当前基金申购状态
     * 1. 申请中
     * 2. 申购成功
     * 3. 申购失败
     */
    private Integer status;

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

    public BigDecimal getProportion() {
        return proportion;
    }

    public void setProportion(BigDecimal proportion) {
        this.proportion = proportion;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public byte getSn() {
        return sn;
    }

    public void setSn(byte sn) {
        this.sn = sn;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("FundCode: %s, fundName: %s, proportion: %s, amount: %s, sn: %s",
                fundCode, fundName, proportion, amount, sn);
    }
}
