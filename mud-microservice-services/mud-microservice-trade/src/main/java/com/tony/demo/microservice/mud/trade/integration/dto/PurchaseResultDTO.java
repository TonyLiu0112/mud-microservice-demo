package com.tony.demo.microservice.mud.trade.integration.dto;

/**
 * Created by Tony on 25/06/2017.
 */
public class PurchaseResultDTO {

    private String applySerialStr;

    private String code;

    private String message;

    public String getApplySerialStr() {
        return applySerialStr;
    }

    public void setApplySerialStr(String applySerialStr) {
        this.applySerialStr = applySerialStr;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
