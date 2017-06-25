package com.tony.demo.microservice.mud.trade.service.bean.req;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 组合申购交易请求
 * <p>
 * Created by Tony on 06/06/2017.
 */
public class FundTradePurchaseRequest {

    /**
     * 组合id
     */
    private Integer portfolioId;

    /**
     * 申购金额
     */
    private String amount;

    /**
     * 组合份额
     */
    private Integer shares = 0;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 身份证号
     */
    private String idNumber;

    /**
     * 交易账号
     */
    private String tradeAcco;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 银行账号
     */
    private String bankAcco;

    /**
     * 银行编号/银行代码
     */
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
    private int aipCycle;

    /**
     * 组合申购基金明细信息
     */
    private List<FundTradePurchaseProperty> fundProperties;

    /**
     * 本次交易手续费
     */
    private BigDecimal fee;

    public FundTradePurchaseRequest() {
        this.fundProperties = new ArrayList<>();
        this.fee = BigDecimal.ZERO;
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
}
