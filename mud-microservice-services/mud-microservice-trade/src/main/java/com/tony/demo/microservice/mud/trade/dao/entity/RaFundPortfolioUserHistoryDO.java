package com.tony.demo.microservice.mud.trade.dao.entity;

import java.math.BigDecimal;
import java.util.Date;

public class RaFundPortfolioUserHistoryDO {
    private Integer userId;

    private Integer portfolioId;

    private Integer recordeDate;

    private BigDecimal portfolioNet;

    private BigDecimal portfolioMarketVal;

    private BigDecimal portfolioInvestedCost;

    private BigDecimal portfolioAccumulatedProfit;

    private Date lastUpdateTime;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(Integer portfolioId) {
        this.portfolioId = portfolioId;
    }

    public Integer getRecordeDate() {
        return recordeDate;
    }

    public void setRecordeDate(Integer recordeDate) {
        this.recordeDate = recordeDate;
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

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}