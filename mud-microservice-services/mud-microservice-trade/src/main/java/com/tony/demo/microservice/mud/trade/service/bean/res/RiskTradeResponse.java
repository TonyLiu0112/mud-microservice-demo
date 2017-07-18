package com.tony.demo.microservice.mud.trade.service.bean.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 风险交易响应对象
 * <p>
 * Created by Tony on 26/06/2017.
 */
@ApiModel
public class RiskTradeResponse {

    /**
     * 是否存在风险
     * <p>
     * <li>true  是</li>
     * <li>false 否</li>
     */
    @ApiModelProperty(notes = "是否存在风险 是:true 否:false")
    private boolean hasRisk;

    public RiskTradeResponse(boolean hasRisk) {
        this.hasRisk = hasRisk;
    }

    public RiskTradeResponse() {
    }

    public boolean isHasRisk() {
        return hasRisk;
    }

    public void setHasRisk(boolean hasRisk) {
        this.hasRisk = hasRisk;
    }
}
