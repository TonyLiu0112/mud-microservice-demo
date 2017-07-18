package com.tony.demo.microservice.mud.trade.exception;

/**
 * 未评测异常
 * <p>
 * Created by Tony on 26/06/2017.
 */
public class UnEvaluatingException extends Exception {
    
    public UnEvaluatingException() {
        super();
    }

    public UnEvaluatingException(String message) {
        super(message);
    }
}
