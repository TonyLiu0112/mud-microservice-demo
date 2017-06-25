package com.tony.demo.microservice.mud.trade.service.bean.req;

import java.math.BigDecimal;
import java.util.List;

/**
 * 组合赎回交易请求
 * <p>
 * Created by Tony on 06/06/2017.
 */
public class FundTradeRedemptionRequest {

    /**
     * 组合id
     */
    private Integer portfolioId;

    /**
     * 赎回比例
     * 0到1的小数
     */
    private String proportion;

    /**
     * 组合中，各个基金按照比例计算出的赎回份额
     */
    private List<FundTradeRedemptionProperty> funds;

    /**
     * 组合份额
     */
    private Integer shares = 0;

    /**
     * 手续费
     */
    private BigDecimal fee;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 银行代码
     */
    private String bankCode;

    /**
     * 银行卡号
     */
    private String bankCardNo;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 身份证号
     */
    private String idNumber;

    /**
     * 交易密码
     */
    private String password;

    /**
     * 交易账号
     */
    private String tradeAcc;

    public FundTradeRedemptionRequest() {
    }

    public FundTradeRedemptionRequest(Integer portfolioId, Integer userId, String proportion) {
        this.portfolioId = portfolioId;
        this.userId = userId;
        this.proportion = proportion;
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

    public String getTradeAcc() {
        return tradeAcc;
    }

    public void setTradeAcc(String tradeAcc) {
        this.tradeAcc = tradeAcc;
    }
}
