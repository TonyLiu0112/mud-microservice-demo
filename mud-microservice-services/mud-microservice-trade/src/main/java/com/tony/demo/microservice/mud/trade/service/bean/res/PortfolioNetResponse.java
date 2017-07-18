package com.tony.demo.microservice.mud.trade.service.bean.res;

import java.util.List;

/**
 * Created by Jianbo on 13/07/2017.
 */
public class PortfolioNetResponse {
    private List<String> netDateArr;
    private List<Float> netArr;
    private String dataName;

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public List<Float> getNetArr() {
        return netArr;
    }

    public void setNetArr(List<Float> netArr) {
        this.netArr = netArr;
    }

    public List<String> getNetDateArr() {
        return netDateArr;
    }

    public void setNetDateArr(List<String> netDateArr) {
        this.netDateArr = netDateArr;
    }
}
