package com.tony.demo.microservice.mud.trade.content;

public enum PartnerCode {
    HBXY("xxxx基金有限公司", "TPF0001"),
    FXB("xxxx人寿保险有限公司", "TPI0001"),;

    private String value;

    private String chName;

    PartnerCode(String chName, String value) {
        this.value = value;
        this.chName = chName;
    }

    public String getCode() {
        return value;
    }

    public String getName() {
        return chName;
    }

    public static PartnerCode getEnum(String code) {
        PartnerCode[] arr = PartnerCode.values();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].getCode().equals(code)) {
                return arr[i];
            }
        }
        return null;
    }
}

