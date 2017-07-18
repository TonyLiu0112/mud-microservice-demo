package com.tony.demo.microservice.mud.trade.service.bean.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 基金申购申请实体
 * <p>
 * Created by Tony on 30/06/2017.
 */
@ApiModel
public class FundTradePurchaseApplyRequest {
    /**
     * 组合id
     */
    @ApiModelProperty(required = true, notes = "申请的组合ID")
    private Integer portfolioId;

    /**
     * 申购金额
     */
    @ApiModelProperty(required = true, notes = "申购金额")
    private String amount;

    /**
     * 银行名称
     */
    @ApiModelProperty(required = true, notes = "银行名称")
    private String bankName;

    /**
     * 银行账号
     */
    @ApiModelProperty(required = true, notes = "银行账号")
    private String bankAcco;

    /**
     * 银行编号/银行代码
     */
    @ApiModelProperty(required = true, notes = "银行代码")
    private String bankSerial;

    /**
     * 已投期数
     */
    @ApiModelProperty(required = true, notes = "已投期数")
    private int aipCycle;

    public Integer getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(Integer portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAcco() {
        return bankAcco;
    }

    public void setBankAcco(String bankAcco) {
        this.bankAcco = bankAcco;
    }

    public String getBankSerial() {
        return bankSerial;
    }

    public void setBankSerial(String bankSerial) {
        this.bankSerial = bankSerial;
    }

    public int getAipCycle() {
        return aipCycle;
    }

    public void setAipCycle(int aipCycle) {
        this.aipCycle = aipCycle;
    }
}
