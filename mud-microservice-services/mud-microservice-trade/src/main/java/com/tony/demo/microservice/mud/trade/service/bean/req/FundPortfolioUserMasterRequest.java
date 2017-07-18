package com.tony.demo.microservice.mud.trade.service.bean.req;

import java.math.BigDecimal;
import java.util.Date;

public class FundPortfolioUserMasterRequest {
    private Integer id;

    private Integer portfolioId;

    private String portfolioName;

    private Byte portfolioRiskType;

    private Integer userId;

    private Short aipCycle;

    private BigDecimal portfolioNet;

    private BigDecimal portfolioMarketVal = BigDecimal.ZERO;

    private BigDecimal portfolioShare = BigDecimal.ZERO;

    private BigDecimal portfolioInvestedCost;

    private BigDecimal portfolioAccumulatedProfit;

    private Integer portfolioTplId;

    private BigDecimal stockFundMarketVal;

    private BigDecimal bondFundMarketVal;

    private BigDecimal currencyFundMarketVal;

    private Byte aipType;

    private Byte rebalanceType;

    private BigDecimal ytdReturn;

    private BigDecimal year1Return;

    private BigDecimal totalReturn;

    private Integer addTime;

    private Date lastUpdateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(Integer portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getPortfolioName() {
        return portfolioName;
    }

    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName == null ? null : portfolioName.trim();
    }

    public Byte getPortfolioRiskType() {
        return portfolioRiskType;
    }

    public void setPortfolioRiskType(Byte portfolioRiskType) {
        this.portfolioRiskType = portfolioRiskType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Short getAipCycle() {
        return aipCycle;
    }

    public void setAipCycle(Short aipCycle) {
        this.aipCycle = aipCycle;
    }

    public BigDecimal getPortfolioNet() {
        return portfolioNet;
    }

    public void setPortfolioNet(BigDecimal portfolioNet) {
        this.portfolioNet = portfolioNet;
    }

    public BigDecimal getPortfolioMarketVal() {
        return portfolioMarketVal;
    }

    public void setPortfolioMarketVal(BigDecimal portfolioMarketVal) {
        this.portfolioMarketVal = portfolioMarketVal;
    }

    public BigDecimal getPortfolioShare() {
        return portfolioShare;
    }

    public void setPortfolioShare(BigDecimal portfolioShare) {
        this.portfolioShare = portfolioShare;
    }

    public BigDecimal getPortfolioInvestedCost() {
        return portfolioInvestedCost;
    }

    public void setPortfolioInvestedCost(BigDecimal portfolioInvestedCost) {
        this.portfolioInvestedCost = portfolioInvestedCost;
    }

    public BigDecimal getPortfolioAccumulatedProfit() {
        return portfolioAccumulatedProfit;
    }

    public void setPortfolioAccumulatedProfit(BigDecimal portfolioAccumulatedProfit) {
        this.portfolioAccumulatedProfit = portfolioAccumulatedProfit;
    }

    public Integer getPortfolioTplId() {
        return portfolioTplId;
    }

    public void setPortfolioTplId(Integer portfolioTplId) {
        this.portfolioTplId = portfolioTplId;
    }

    public BigDecimal getStockFundMarketVal() {
        return stockFundMarketVal;
    }

    public void setStockFundMarketVal(BigDecimal stockFundMarketVal) {
        this.stockFundMarketVal = stockFundMarketVal;
    }

    public BigDecimal getBondFundMarketVal() {
        return bondFundMarketVal;
    }

    public void setBondFundMarketVal(BigDecimal bondFundMarketVal) {
        this.bondFundMarketVal = bondFundMarketVal;
    }

    public BigDecimal getCurrencyFundMarketVal() {
        return currencyFundMarketVal;
    }

    public void setCurrencyFundMarketVal(BigDecimal currencyFundMarketVal) {
        this.currencyFundMarketVal = currencyFundMarketVal;
    }

    public Byte getAipType() {
        return aipType;
    }

    public void setAipType(Byte aipType) {
        this.aipType = aipType;
    }

    public Byte getRebalanceType() {
        return rebalanceType;
    }

    public void setRebalanceType(Byte rebalanceType) {
        this.rebalanceType = rebalanceType;
    }

    public BigDecimal getYtdReturn() {
        return ytdReturn;
    }

    public void setYtdReturn(BigDecimal ytdReturn) {
        this.ytdReturn = ytdReturn;
    }

    public BigDecimal getYear1Return() {
        return year1Return;
    }

    public void setYear1Return(BigDecimal year1Return) {
        this.year1Return = year1Return;
    }

    public BigDecimal getTotalReturn() {
        return totalReturn;
    }

    public void setTotalReturn(BigDecimal totalReturn) {
        this.totalReturn = totalReturn;
    }

    public Integer getAddTime() {
        return addTime;
    }

    public void setAddTime(Integer addTime) {
        this.addTime = addTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}