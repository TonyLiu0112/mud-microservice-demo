package com.tony.demo.microservice.mud.trade.service.bean.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 组合申购交易请求
 * <p>
 * Created by Tony on 06/06/2017.
 */
@ApiModel
public class FundTradePurchaseRequest {

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
     * 组合份额
     */
    @ApiModelProperty(required = true, notes = "组合份额")
    private Integer shares = 0;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 身份证号
     */
    @ApiModelProperty(required = true, notes = "用户身份证")
    private String idNumber;

    /**
     * 交易账号
     */
    @ApiModelProperty(required = true, notes = "交易账号")
    private String tradeAcco;

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
     * 资金模式
     */
    private String capitalMode;

    /**
     * 交易密码
     */
    private String password;

    /**
     * 已投期数
     */
    @ApiModelProperty(required = true, notes = "投资期数")
    private int aipCycle;

    /**
     * 组合申购基金明细信息
     */
    @ApiModelProperty(required = true, notes = "组合基金申请明细")
    private List<FundTradePurchaseProperty> fundProperties;

    /**
     * 本次交易手续费
     */
    @ApiModelProperty(required = true, notes = "手续费")
    private BigDecimal fee = BigDecimal.ZERO;

    /**
     * 各个基金手续费
     */
    private Map<String, BigDecimal> fundFees;

    public FundTradePurchaseRequest() {
        this.fundProperties = new ArrayList<>();
        this.fundFees = new HashMap<>();
    }

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Integer getShares() {
        return shares;
    }

    public void setShares(Integer shares) {
        this.shares = shares;
    }

    public String getTradeAcco() {
        return tradeAcco;
    }

    public void setTradeAcco(String tradeAcco) {
        this.tradeAcco = tradeAcco;
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

    public String getCapitalMode() {
        return capitalMode;
    }

    public void setCapitalMode(String capitalMode) {
        this.capitalMode = capitalMode;
    }

    public List<FundTradePurchaseProperty> getFundProperties() {
        return fundProperties;
    }

    public void setFundProperties(List<FundTradePurchaseProperty> fundProperties) {
        this.fundProperties = fundProperties;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public int getAipCycle() {
        return aipCycle;
    }

    public void setAipCycle(int aipCycle) {
        this.aipCycle = aipCycle;
    }

    public Map<String, BigDecimal> getFundFees() {
        return fundFees;
    }

    public void setFundFees(Map<String, BigDecimal> fundFees) {
        this.fundFees = fundFees;
    }
}
