package com.tony.demo.microservice.mud.trade.service.bean.req;

/**
 * Created by Tony on 07/06/2017.
 */
public class SingleFundTradeRequest {

    /**
     * 申购金额
     */
    private String amount;

    /**
     * 银行代码
     */
    private String bankCode;

    /**
     * 银行卡号
     */
    private String bankCardNo;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 交易密码
     */
    private String password;

    /**
     * 基金代码
     */
    private String fundCode;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
    }
}
