package com.tony.demo.microservice.mud.trade.service.bean.res;

/**
 * Created by Jianbo on 13/07/2017.
 */
public class PortfolioNetTrendResponse {
    private PortfolioNetResponse lastMonth;
    private PortfolioNetResponse lastThreeMonths;
    private PortfolioNetResponse lastHalfYear;
    private PortfolioNetResponse lastYear;

    public PortfolioNetResponse getLastMonth() {
        return lastMonth;
    }

    public void setLastMonth(PortfolioNetResponse lastMonth) {
        this.lastMonth = lastMonth;
    }

    public PortfolioNetResponse getLastThreeMonths() {
        return lastThreeMonths;
    }

    public void setLastThreeMonths(PortfolioNetResponse lastThreeMonths) {
        this.lastThreeMonths = lastThreeMonths;
    }

    public PortfolioNetResponse getLastHalfYear() {
        return lastHalfYear;
    }

    public void setLastHalfYear(PortfolioNetResponse lastHalfYear) {
        this.lastHalfYear = lastHalfYear;
    }

    public PortfolioNetResponse getLastYear() {
        return lastYear;
    }

    public void setLastYear(PortfolioNetResponse lastYear) {
        this.lastYear = lastYear;
    }
}
