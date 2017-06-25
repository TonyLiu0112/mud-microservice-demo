package com.tony.demo.microservice.mud.trade.exception;

/**
 * 无效参数异常
 * <p>
 * Created by Tony on 12/06/2017.
 */
public class InvalidParameterException extends Exception {

    public InvalidParameterException() {
    }

    public InvalidParameterException(String message) {
        super(message);
    }
}
