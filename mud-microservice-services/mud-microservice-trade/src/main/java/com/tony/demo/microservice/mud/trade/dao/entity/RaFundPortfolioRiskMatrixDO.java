package com.tony.demo.microservice.mud.trade.dao.entity;

public class RaFundPortfolioRiskMatrixDO {
    private Integer id;

    private Byte investmentPeriod;

    private Byte riskPrefer;

    private Integer portfolioId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getInvestmentPeriod() {
        return investmentPeriod;
    }

    public void setInvestmentPeriod(Byte investmentPeriod) {
        this.investmentPeriod = investmentPeriod;
    }

    public Byte getRiskPrefer() {
        return riskPrefer;
    }

    public void setRiskPrefer(Byte riskPrefer) {
        this.riskPrefer = riskPrefer;
    }

    public Integer getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(Integer portfolioId) {
        this.portfolioId = portfolioId;
    }
}