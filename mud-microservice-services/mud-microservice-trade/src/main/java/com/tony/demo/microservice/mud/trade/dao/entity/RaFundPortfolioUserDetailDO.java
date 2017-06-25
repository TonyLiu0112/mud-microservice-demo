package com.tony.demo.microservice.mud.trade.dao.entity;

import java.math.BigDecimal;

public class RaFundPortfolioUserDetailDO {
    private Integer id;

    private Integer portfolioId;

    private String fundCode;

    private Integer userId;

    private Byte sn;

    private BigDecimal percentBegin;

    private BigDecimal percent;

    private BigDecimal marketVal;

    private BigDecimal share;

    private String shareId;

    private BigDecimal cost;

    private String bankCode;

    private String bankCardNo;

    private String bankName;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public BigDecimal getPercent() {
        return percent;
    }

    public void setPercent(BigDecimal percent) {
        this.percent = percent;
    }

    public BigDecimal getMarketVal() {
        return marketVal;
    }

    public void setMarketVal(BigDecimal marketVal) {
        this.marketVal = marketVal;
    }

    public BigDecimal getShare() {
        return share;
    }

    public void setShare(BigDecimal share) {
        this.share = share;
    }

    public String getShareId() {
        return shareId;
    }

    public void setShareId(String shareId) {
        this.shareId = shareId == null ? null : shareId.trim();
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode == null ? null : bankCode.trim();
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo == null ? null : bankCardNo.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}