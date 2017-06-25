package com.tony.demo.microservice.mud.trade.exception;

/**
 * 交易密码错误异常
 * <p>
 * Created by Tony on 20/06/2017.
 */
public class TradePasswordErrorException extends Exception {

    public TradePasswordErrorException() {
        super("交易密码错误.");
    }

    public TradePasswordErrorException(String message) {
        super(message);
    }
}
