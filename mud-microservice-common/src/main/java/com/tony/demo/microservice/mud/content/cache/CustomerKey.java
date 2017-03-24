package com.tony.demo.microservice.mud.content.cache;

/**
 * 客户信息缓存key
 * 客户信息已hash形式存储
 * <p>
 * Created by Tony on 07/12/2016.
 */
public enum CustomerKey {

    KEY("CUSTOMER_", "客户hash结构主Key"),
    FIELD_ID("ID", "主键id"),
    FIELD_NAME("NAME", "公司名称"),
    FIELD_CODE("CODE", "公司编码"),
    FIELD_ADDRESS("ADDRESS", "公司地址"),
    FIELD_CONTACT_PHONE("CONTACT_PHONE", "公司联系人手机"),
    FIELD_CONTACT_NAME("CONTACT_NAME", "公司联系人"),
    FIELD_INDUSTRY_CODE("INDUSTRY_CODE", "行业编码"),
    FIELD_INDUSTRY_NAME("INDUSTRY_NAME", "行业名称");


    private String keyName;

    private String desc;

    CustomerKey(String keyName, String desc) {
        this.keyName = keyName;
        this.desc = desc;
    }

    public String key() {
        return keyName;
    }
}
