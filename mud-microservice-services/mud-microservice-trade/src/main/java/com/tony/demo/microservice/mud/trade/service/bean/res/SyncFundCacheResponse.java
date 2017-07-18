package com.tony.demo.microservice.mud.trade.service.bean.res;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 同步基金数据中间缓存对象模型
 * <p>
 * Created by Tony on 29/06/2017.
 */
public class SyncFundCacheResponse {

    /**
     * 基金代码
     */
    private String fundCode;

    /**
     * 用户id
     */
    private int userId;

    /**
     * 身份证号码
     */
    private String idNumber;

    /**
     * 基金权重
     * key:   基金组合id
     * value: 在当前组合中所占的权重比例(0~1小数)
     */
    private Map<Integer, BigDecimal> weightMap;

    /**
     * 基金昨日收益
     */
    private BigDecimal lastdayincome = BigDecimal.ZERO;

    /**
     * 基金今年以来收益
     */
    private BigDecimal thisyearincome = BigDecimal.ZERO;

    /**
     * 基金累计收益
     */
    private BigDecimal totalincome = BigDecimal.ZERO;

    /**
     * 基金份额
     */
    private BigDecimal share = BigDecimal.ZERO;

    /**
     * 基金市值
     */
    private BigDecimal marketVal = BigDecimal.ZERO;

    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Map<Integer, BigDecimal> getWeightMap() {
        return weightMap;
    }

    public void setWeightMap(Map<Integer, BigDecimal> weightMap) {
        this.weightMap = weightMap;
    }

    public BigDecimal getShare() {
        return share;
    }

    public void setShare(BigDecimal share) {
        this.share = share;
    }

    public BigDecimal getMarketVal() {
        return marketVal;
    }

    public void setMarketVal(BigDecimal marketVal) {
        this.marketVal = marketVal;
    }

    public BigDecimal getLastdayincome() {
        return lastdayincome;
    }

    public void setLastdayincome(BigDecimal lastdayincome) {
        this.lastdayincome = lastdayincome;
    }

    public BigDecimal getThisyearincome() {
        return thisyearincome;
    }

    public void setThisyearincome(BigDecimal thisyearincome) {
        this.thisyearincome = thisyearincome;
    }

    public BigDecimal getTotalincome() {
        return totalincome;
    }

    public void setTotalincome(BigDecimal totalincome) {
        this.totalincome = totalincome;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
}
