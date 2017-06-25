package com.tony.demo.microservice.mud.trade.dao.entity;

public class RaFundPortfolioRiskTestMappingDO {
    private Integer id;

    private Integer scoreBegin;

    private Integer scoreEnd;

    private Byte riskPrefer;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getScoreBegin() {
        return scoreBegin;
    }

    public void setScoreBegin(Integer scoreBegin) {
        this.scoreBegin = scoreBegin;
    }

    public Integer getScoreEnd() {
        return scoreEnd;
    }

    public void setScoreEnd(Integer scoreEnd) {
        this.scoreEnd = scoreEnd;
    }

    public Byte getRiskPrefer() {
        return riskPrefer;
    }

    public void setRiskPrefer(Byte riskPrefer) {
        this.riskPrefer = riskPrefer;
    }
}