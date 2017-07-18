package com.tony.demo.microservice.mud.trade.dao.repository;

import java.math.BigDecimal;

/**
 * 组合收益
 * <p>
 * Created by Tony on 11/07/2017.
 */
public class PortfolioIncomeRO {

    private String portfolioName;

    private String date;

    private BigDecimal income;

    public String getPortfolioName() {
        return portfolioName;
    }

    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }
}
