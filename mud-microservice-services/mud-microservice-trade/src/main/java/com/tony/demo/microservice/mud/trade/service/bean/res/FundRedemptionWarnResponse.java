package com.tony.demo.microservice.mud.trade.service.bean.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 基金赎回年限提示
 * <p>
 * Created by Tony on 26/06/2017.
 */
@ApiModel
public class FundRedemptionWarnResponse {

    /**
     * 是否提示
     */
    @ApiModelProperty(notes = "是否提示: true 提示，false 不提示")
    private boolean isWarn;

    /**
     * 用户持有申购年限
     */
    @ApiModelProperty(notes = "用户当前赎回基金的年限")
    private int yearLimit;

    public FundRedemptionWarnResponse() {
    }

    public FundRedemptionWarnResponse(boolean isWarn, int yearLimit) {
        this.isWarn = isWarn;
        this.yearLimit = yearLimit;
    }

    public boolean isWarn() {
        return isWarn;
    }

    public void setWarn(boolean warn) {
        isWarn = warn;
    }

    public int getYearLimit() {
        return yearLimit;
    }

    public void setYearLimit(int yearLimit) {
        this.yearLimit = yearLimit;
    }
}
