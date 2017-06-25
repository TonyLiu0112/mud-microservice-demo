package com.tony.demo.microservice.mud.trade.dao.entity;

public class RaFundPortfolioRiskTestResultDO {
    private Integer id;

    private Integer userId;

    private Integer score;

    private Byte riskPrefer;

    private Byte customRiskPrefer;

    private Byte investmentPeriod;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Byte getRiskPrefer() {
        return riskPrefer;
    }

    public void setRiskPrefer(Byte riskPrefer) {
        this.riskPrefer = riskPrefer;
    }

    public Byte getCustomRiskPrefer() {
        return customRiskPrefer;
    }

    public void setCustomRiskPrefer(Byte customRiskPrefer) {
        this.customRiskPrefer = customRiskPrefer;
    }

    public Byte getInvestmentPeriod() {
        return investmentPeriod;
    }

    public void setInvestmentPeriod(Byte investmentPeriod) {
        this.investmentPeriod = investmentPeriod;
    }
}