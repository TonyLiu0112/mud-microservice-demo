package com.tony.demo.microservice.mud.trade.dao.entity;

import java.math.BigDecimal;

public class RaFundPortfolioMasterDO {
    private Integer portfolioId;

    private String portfolioName;

    private Byte riskPrefer;

    private Byte investmentPeriod;

    private String portfolioProposal;

    private String portfolioAdvice;

    private BigDecimal bondFundPercent;

    private BigDecimal stockFundPercent;

    private BigDecimal currencyFundPercent;

    private BigDecimal ytdReturn;

    private BigDecimal year1Return;

    private BigDecimal totalReturn;

    private BigDecimal compoundAnnualRate;

    private BigDecimal bearMarketReturn;

    private String compareIndex1;

    private String compareIndex2;

    private String compareIndex3;

    private Byte status;

    private BigDecimal futrueBestAnnualReturn;

    private BigDecimal futrueWorstAnnualReturn;

    private BigDecimal minPurchaseAmt;

    private BigDecimal minFeeRate;

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

    public Byte getRiskPrefer() {
        return riskPrefer;
    }

    public void setRiskPrefer(Byte riskPrefer) {
        this.riskPrefer = riskPrefer;
    }

    public Byte getInvestmentPeriod() {
        return investmentPeriod;
    }

    public void setInvestmentPeriod(Byte investmentPeriod) {
        this.investmentPeriod = investmentPeriod;
    }

    public String getPortfolioProposal() {
        return portfolioProposal;
    }

    public void setPortfolioProposal(String portfolioProposal) {
        this.portfolioProposal = portfolioProposal == null ? null : portfolioProposal.trim();
    }

    public String getPortfolioAdvice() {
        return portfolioAdvice;
    }

    public void setPortfolioAdvice(String portfolioAdvice) {
        this.portfolioAdvice = portfolioAdvice == null ? null : portfolioAdvice.trim();
    }

    public BigDecimal getBondFundPercent() {
        return bondFundPercent;
    }

    public void setBondFundPercent(BigDecimal bondFundPercent) {
        this.bondFundPercent = bondFundPercent;
    }

    public BigDecimal getStockFundPercent() {
        return stockFundPercent;
    }

    public void setStockFundPercent(BigDecimal stockFundPercent) {
        this.stockFundPercent = stockFundPercent;
    }

    public BigDecimal getCurrencyFundPercent() {
        return currencyFundPercent;
    }

    public void setCurrencyFundPercent(BigDecimal currencyFundPercent) {
        this.currencyFundPercent = currencyFundPercent;
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

    public BigDecimal getCompoundAnnualRate() {
        return compoundAnnualRate;
    }

    public void setCompoundAnnualRate(BigDecimal compoundAnnualRate) {
        this.compoundAnnualRate = compoundAnnualRate;
    }

    public BigDecimal getBearMarketReturn() {
        return bearMarketReturn;
    }

    public void setBearMarketReturn(BigDecimal bearMarketReturn) {
        this.bearMarketReturn = bearMarketReturn;
    }

    public String getCompareIndex1() {
        return compareIndex1;
    }

    public void setCompareIndex1(String compareIndex1) {
        this.compareIndex1 = compareIndex1 == null ? null : compareIndex1.trim();
    }

    public String getCompareIndex2() {
        return compareIndex2;
    }

    public void setCompareIndex2(String compareIndex2) {
        this.compareIndex2 = compareIndex2 == null ? null : compareIndex2.trim();
    }

    public String getCompareIndex3() {
        return compareIndex3;
    }

    public void setCompareIndex3(String compareIndex3) {
        this.compareIndex3 = compareIndex3 == null ? null : compareIndex3.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public BigDecimal getFutrueBestAnnualReturn() {
        return futrueBestAnnualReturn;
    }

    public void setFutrueBestAnnualReturn(BigDecimal futrueBestAnnualReturn) {
        this.futrueBestAnnualReturn = futrueBestAnnualReturn;
    }

    public BigDecimal getFutrueWorstAnnualReturn() {
        return futrueWorstAnnualReturn;
    }

    public void setFutrueWorstAnnualReturn(BigDecimal futrueWorstAnnualReturn) {
        this.futrueWorstAnnualReturn = futrueWorstAnnualReturn;
    }

    public BigDecimal getMinPurchaseAmt() {
        return minPurchaseAmt;
    }

    public void setMinPurchaseAmt(BigDecimal minPurchaseAmt) {
        this.minPurchaseAmt = minPurchaseAmt;
    }

    public BigDecimal getMinFeeRate() {
        return minFeeRate;
    }

    public void setMinFeeRate(BigDecimal minFeeRate) {
        this.minFeeRate = minFeeRate;
    }
}