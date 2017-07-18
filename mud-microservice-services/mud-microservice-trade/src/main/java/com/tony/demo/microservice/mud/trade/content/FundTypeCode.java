package com.tony.demo.microservice.mud.trade.content;

/**
 * 2 monetary fund
 * 3 bond fund
 * 4 A share fund
 * 5 overseas assets
 * 6 Hong Kong stock fund
 * 7 other
 * Created by Tony on 12/07/2017.
 */
public enum FundTypeCode {
    MONEY_FUND(2, "货币基金"),
    BOND_FUND(3, "债券基金"),
    A_FUND(4, "A股基金"),
    OVERSEAS_ASSETS(5, "海外资产"),
    HK_FUND(6, "港股基金"),
    OTHER_FUND(7, "其他"),;

    private int type;

    private String name;

    FundTypeCode(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static FundTypeCode getByType(int type) {
        for (FundTypeCode fundTypeCode : FundTypeCode.values()) {
            if (fundTypeCode.getType() == type) {
                return fundTypeCode;
            }
        }
        return null;
    }
}
