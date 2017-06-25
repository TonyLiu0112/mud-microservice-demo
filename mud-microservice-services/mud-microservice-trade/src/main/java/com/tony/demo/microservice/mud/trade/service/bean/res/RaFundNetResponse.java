package com.tony.demo.microservice.mud.trade.service.bean.res;

import java.math.BigDecimal;
import java.util.Date;

public class RaFundNetResponse {
    private Integer id;

    private String fundCode;

    private String fundName;

    private BigDecimal net;

    private Date netDate;

    private BigDecimal accumulativeNet;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        this.fundName = fundName == null ? null : fundName.trim();
    }

    public BigDecimal getNet() {
        return net;
    }

    public void setNet(BigDecimal net) {
        this.net = net;
    }

    public Date getNetDate() {
        return netDate;
    }

    public void setNetDate(Date netDate) {
        this.netDate = netDate;
    }

    public BigDecimal getAccumulativeNet() {
        return accumulativeNet;
    }

    public void setAccumulativeNet(BigDecimal accumulativeNet) {
        this.accumulativeNet = accumulativeNet;
    }
}