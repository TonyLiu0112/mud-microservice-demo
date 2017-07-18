package com.tony.demo.microservice.mud.trade.service.bean.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 组合赎回交易请求
 * <p>
 * Created by Tony on 06/06/2017.
 */
@ApiModel(description = "组合赎回对象")
public class FundTradeRedemptionRequest {

    /**
     * 组合id
     */
    @ApiModelProperty(required = true, notes = "组合ID")
    private Integer portfolioId;

    /**
     * 赎回比例
     * 0到1的小数
     */
    @ApiModelProperty(required = true, notes = "组合赎回比例(0-1小数)")
    private String proportion;

    /**
     * 组合中，各个基金按照比例计算出的赎回份额
     */
    @ApiModelProperty(required = true, notes = "赎回各个基金按照比例计算出的份额信息")
    private List<FundTradeRedemptionProperty> funds;

    /**
     * 组合份额
     */
    @ApiModelProperty(required = true, notes = "组合份额")
    private Integer shares = 0;

    /**
     * 手续费
     */
    @ApiModelProperty(required = true, notes = "手续费")
    private BigDecimal fee = BigDecimal.ZERO;

    /**
     * 各个基金手续费
     */
    private Map<String, BigDecimal> fundFees;

    /**
     * 银行名称
     */
    @ApiModelProperty(required = true, notes = "银行名称")
    private String bankName;

    /**
     * 银行代码
     */
    @ApiModelProperty(required = true, notes = "银行代码")
    private String bankCode;

    /**
     * 银行卡号
     */
    @ApiModelProperty(required = true, notes = "银行卡号")
    private String bankCardNo;

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
     * 交易密码
     */
    private String password;

    /**
     * 交易账号
     */
    @ApiModelProperty(required = true, notes = "交易账号")
    private String tradeAcco;

    public FundTradeRedemptionRequest() {
        fundFees = new HashMap<>();
    }

    public FundTradeRedemptionRequest(Integer portfolioId, Integer userId, String proportion) {
        this.portfolioId = portfolioId;
        this.userId = userId;
        this.proportion = proportion;
        fundFees = new HashMap<>();
    }

    public Integer getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(Integer portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getProportion() {
        return proportion;
    }

    public void setProportion(String proportion) {
        this.proportion = proportion;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
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

    public List<FundTradeRedemptionProperty> getFunds() {
        return funds;
    }

    public void setFunds(List<FundTradeRedemptionProperty> funds) {
        this.funds = funds;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getTradeAcco() {
        return tradeAcco;
    }

    public void setTradeAcco(String tradeAcco) {
        this.tradeAcco = tradeAcco;
    }

    public Map<String, BigDecimal> getFundFees() {
        return fundFees;
    }

    public void setFundFees(Map<String, BigDecimal> fundFees) {
        this.fundFees = fundFees;
    }
}
