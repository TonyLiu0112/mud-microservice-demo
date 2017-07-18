/**
 * 
 *	X理财
 * Copyright (c) 2013-2015 Tony,Inc.All Rights Reserved.
 */
package com.tony.demo.microservice.mud.trade.content;
/**
 * 
 */
public enum AppTypeCode {
    WEB_SITE_XLICAI("X理财网站", 1),
    WE_CHAT_XLICAI("X理财微信", 2),
    APP_XLICAI("X理财app", 3),
;

    private Integer value;

    private String name;
    
    private AppTypeCode(String chName, Integer value) {
        this.value = value;
        this.name = chName;
    }

    public Integer getCode() {
        return value;
    }

    public String getName() {
        return name;
    }
    
    public static AppTypeCode getEnum(int code){
    	AppTypeCode[] arr = AppTypeCode.values();
    	for (int i = 0; i < arr.length; i++) {
			if(arr[i].getCode().equals(code)){
				return arr[i];
			}
		}
    	return null;
    }
}

