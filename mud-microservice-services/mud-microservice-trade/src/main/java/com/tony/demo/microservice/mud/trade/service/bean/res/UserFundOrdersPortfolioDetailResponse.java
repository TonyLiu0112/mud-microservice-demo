package com.tony.demo.microservice.mud.trade.service.bean.res;

import java.math.BigDecimal;
import java.util.Date;

public class UserFundOrdersPortfolioDetailResponse {
    private String orderId;

    private Integer orderMasterId;

    private String fundCode;

    private Integer userId;

    private String ttAccountNo;

    private String ttOrderNo;

    private Integer portfolioId;

    private BigDecimal amount;

    private BigDecimal share;

    private String shareId;

    private Integer orderTime;

    private Integer applyTime;

    private Integer orderType;

    private Integer workday;

    private Integer status;

    private String bankCode;

    private String bankCardNo;

    private BigDecimal fee;

    private Integer payStatus;

    private Integer confirmDate;

    private BigDecimal comfirmAmount;

    private BigDecimal comfirmShare;

    private BigDecimal comfirmNav;

    private String failReason;

    private Date lastUpdateTime;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public Integer getOrderMasterId() {
        return orderMasterId;
    }

    public void setOrderMasterId(Integer orderMasterId) {
        this.orderMasterId = orderMasterId;
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

    public String getTtAccountNo() {
        return ttAccountNo;
    }

    public void setTtAccountNo(String ttAccountNo) {
        this.ttAccountNo = ttAccountNo == null ? null : ttAccountNo.trim();
    }

    public String getTtOrderNo() {
        return ttOrderNo;
    }

    public void setTtOrderNo(String ttOrderNo) {
        this.ttOrderNo = ttOrderNo == null ? null : ttOrderNo.trim();
    }

    public Integer getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(Integer portfolioId) {
        this.portfolioId = portfolioId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    public Integer getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Integer orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Integer applyTime) {
        this.applyTime = applyTime;
    }

    public Integer getWorkday() {
        return workday;
    }

    public void setWorkday(Integer workday) {
        this.workday = workday;
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

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public Integer getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Integer confirmDate) {
        this.confirmDate = confirmDate;
    }

    public BigDecimal getComfirmAmount() {
        return comfirmAmount;
    }

    public void setComfirmAmount(BigDecimal comfirmAmount) {
        this.comfirmAmount = comfirmAmount;
    }

    public BigDecimal getComfirmShare() {
        return comfirmShare;
    }

    public void setComfirmShare(BigDecimal comfirmShare) {
        this.comfirmShare = comfirmShare;
    }

    public BigDecimal getComfirmNav() {
        return comfirmNav;
    }

    public void setComfirmNav(BigDecimal comfirmNav) {
        this.comfirmNav = comfirmNav;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason == null ? null : failReason.trim();
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }
}