package com.tony.demo.microservice.mud.trade.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * 业务异常
 * @author Administrator
 *
 */
public class BusinessException extends Exception { 
	
	public BusinessException(){
		super();
	}
	public BusinessException (String msg){   
	  super(msg);   
	}
	
	public BusinessException(String msg, Throwable ex){   
	   super(msg,ex);   
	}   
	      
	public String getMessage(){   
	   String message = super.getMessage();   
	   Throwable cause = getCause();   
	   if(cause != null){   
	     message = message + ";BusinessException is " + cause;   
	   }   
	   return message;   
	}   
	
	public void printStackTrace(PrintStream ps){   
	   if(getCause() == null){   
	     super.printStackTrace(ps);    
	   }else{   
	     ps.println(this);   
	     getCause().printStackTrace(ps);   
	   }   
	}   
	    
	public void printStackTrace(PrintWriter pw){   
	   if(getCause() == null){   
	      super.printStackTrace(pw);   
	   }else{   
	      pw.println(this);   
	      getCause().printStackTrace(pw);   
	   }   
	}   
	public void printStackTrace(){   
	   printStackTrace(System.err);   
	}  
}
