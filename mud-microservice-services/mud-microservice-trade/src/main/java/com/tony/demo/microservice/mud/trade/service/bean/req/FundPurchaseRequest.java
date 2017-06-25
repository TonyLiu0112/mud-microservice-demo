package com.tony.demo.microservice.mud.trade.service.bean.req;

/**
 * 申购请求
 * <p>
 * Created by Tony on 06/06/2017.
 */
public class FundPurchaseRequest {

    /**
     * 申购金额
     */
    private String amount;

    /**
     * 份额
     */
    private Integer shares = 0;

    /**
     * 银行代码
     */
    private String bankCode;

    /**
     * 银行卡号
     */
    private String bankCardNo;

    /**
     * 身份证号
     */
    private String idNumber;

    /**
     * 交易密码
     */
    private String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Integer getShares() {
        return shares;
    }

    public void setShares(Integer shares) {
        this.shares = shares;
    }
}
