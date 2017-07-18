package com.tony.demo.microservice.mud.trade.service.bean.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * 组合收益
 * <p>
 * Created by Tony on 11/07/2017.
 */
@ApiModel
public class PortfolioIncomeResponse {

    @ApiModelProperty(notes = "组合名称")
    private String portfolioName;

    @ApiModelProperty(notes = "收益日期")
    private String date;

    @ApiModelProperty(notes = "收益金额")
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
