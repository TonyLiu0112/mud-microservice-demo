package com.tony.demo.microservice.mud.trade.exception;

/**
 * 调用基金公司申购基金异常
 * <p>
 * Created by Tony on 12/06/2017.
 */
public class RemotePurchaseException extends Exception {

    public RemotePurchaseException() {
    }

    public RemotePurchaseException(String message) {
        super(message);
    }
}
