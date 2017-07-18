package com.tony.demo.microservice.mud.trade.service.bean.res;

/**
 * 交易流水同步结果
 * <p>
 * Created by Tony on 28/06/2017.
 */
public class SyncOrderResponse {

    /**
     * 是否成功
     */
    private boolean isSuccess;

    /**
     * 申请份额
     */
    private String applyShare;

    /**
     * 申请金额
     */
    private String applySum;

    /**
     * 确认份额
     */
    private String confirmShare;

    /**
     * 确认金额
     */
    private String confirmSum;

    /**
     * 1 申购
     * 2 赎回
     */
    private int orderType;

    public SyncOrderResponse(boolean isSuccess, int orderType) {
        this.isSuccess = isSuccess;
        this.orderType = orderType;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getApplyShare() {
        return applyShare;
    }

    public void setApplyShare(String applyShare) {
        this.applyShare = applyShare;
    }

    public String getApplySum() {
        return applySum;
    }

    public void setApplySum(String applySum) {
        this.applySum = applySum;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public String getConfirmShare() {
        return confirmShare;
    }

    public void setConfirmShare(String confirmShare) {
        this.confirmShare = confirmShare;
    }

    public String getConfirmSum() {
        return confirmSum;
    }

    public void setConfirmSum(String confirmSum) {
        this.confirmSum = confirmSum;
    }
}
