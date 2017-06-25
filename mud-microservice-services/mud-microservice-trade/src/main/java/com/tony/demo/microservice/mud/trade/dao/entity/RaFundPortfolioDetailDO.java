package com.tony.demo.microservice.mud.trade.dao.entity;

import java.math.BigDecimal;
import java.util.Date;

public class RaFundPortfolioDetailDO {
    private Integer id;

    private Integer portfolioId;

    private String fundCode;

    private String fundName;

    private Byte fundType;

    private Byte sn;

    private BigDecimal percentBegin;

    private Integer addTime;

    private Date lastUpdateTime;

    private String fundCodeReplace;

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

    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(String fundCode) {
        this.fundCode = fundCode == null ? null : fundCode.trim();
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public Byte getFundType() {
        return fundType;
    }

    public void setFundType(Byte fundType) {
        this.fundType = fundType;
    }

    public Byte getSn() {
        return sn;
    }

    public void setSn(Byte sn) {
        this.sn = sn;
    }

    public BigDecimal getPercentBegin() {
        return percentBegin;
    }

    public void setPercentBegin(BigDecimal percentBegin) {
        this.percentBegin = percentBegin;
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

    public String getFundCodeReplace() {
        return fundCodeReplace;
    }

    public void setFundCodeReplace(String fundCodeReplace) {
        this.fundCodeReplace = fundCodeReplace == null ? null : fundCodeReplace.trim();
    }
}