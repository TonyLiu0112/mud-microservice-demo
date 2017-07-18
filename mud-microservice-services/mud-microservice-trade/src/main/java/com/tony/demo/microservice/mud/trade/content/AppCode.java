/**
 * X理财
 * Copyright (c) 2013-2015 Tony,Inc.All Rights Reserved.
 */
package com.tony.demo.microservice.mud.trade.content;

public enum AppCode {
    APP_ID_XLICAI("X理财", "APP0001");
    private String value;

    private String chName;

    private AppCode(String chName, String value) {
        this.value = value;
        this.chName = chName;
    }

    public String getCode() {
        return value;
    }

    public String getName() {
        return chName;
    }

    public static AppCode getEnum(String code) {
        AppCode[] arr = AppCode.values();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].getCode().equals(code)) {
                return arr[i];
            }
        }
        return null;
    }
}

