package com.tony.demo.microservice.mud.trade.exception;

/**
 * 远程目标调用异常
 * <p>
 * Created by Tony on 12/06/2017.
 */
public class RemoteInvokeException extends Exception {

    public RemoteInvokeException() {
    }

    public RemoteInvokeException(String message) {
        super(message);
    }
}
