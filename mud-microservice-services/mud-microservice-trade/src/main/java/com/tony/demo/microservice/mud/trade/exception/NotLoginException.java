package com.tony.demo.microservice.mud.trade.exception;
/**
 * session过期异常
 * @author Administrator
 *
 */
public class NotLoginException extends BusinessException {

	public NotLoginException(){   
		super();
	}   

	public NotLoginException(String msg){   
		super(msg);   
	}   

	public NotLoginException(String msg, Throwable ex){   
		super(msg,ex);
	}

	@Override
	public String getMessage() {
		String message = super.getMessage();   
		Throwable cause = getCause();   
		if(cause != null){   
			message = message + ";NotLoginException is " + cause;   
		}   
		return message; 
	} 
}
