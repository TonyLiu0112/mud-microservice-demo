package com.tony.demo.microservice.mud.trade.service.bean.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 组合明细响应对象
 */
@ApiModel
public class FundPortfolioDetailResponse {

    @ApiModelProperty(notes = "组合ID")
    private Integer portfolioId;

    @ApiModelProperty(notes = "组合名称")
    private String portfolioName;

    @ApiModelProperty(notes = "饼图数据集")
    private List<Holder> data;

    @ApiModelProperty(notes = "各基金占比; k: 基金名称 v: 占比")
    private Map<String, BigDecimal> funds;

    @ApiModelProperty(notes = "组合净值历史趋势（折线图）")
    private PortfolioNetTrendResponse netTrend;

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
        this.portfolioName = portfolioName;
    }

    public List<Holder> getData() {
        return data;
    }

    public void setData(List<Holder> data) {
        this.data = data;
    }

    public Map<String, BigDecimal> getFunds() {
        return funds;
    }

    public void setFunds(Map<String, BigDecimal> funds) {
        this.funds = funds;
    }

    public PortfolioNetTrendResponse getNetTrend() {
        return netTrend;
    }

    public void setNetTrend(PortfolioNetTrendResponse netTrend) {
        this.netTrend = netTrend;
    }

    @ApiModel(description = "饼图占比数据")
    public class Holder {

        @ApiModelProperty("比例")
        private BigDecimal value;

        @ApiModelProperty("占比")
        private String name;

        public BigDecimal getValue() {
            return value;
        }

        public void setValue(BigDecimal value) {
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}