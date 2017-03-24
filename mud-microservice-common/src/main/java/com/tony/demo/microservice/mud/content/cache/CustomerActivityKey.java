package com.tony.demo.microservice.mud.content.cache;

/**
 * Created by Tony on 17/02/2017.
 */
public enum CustomerActivityKey {

    KEY("CUSTOMER_ACTIVITY_", "客户活动缓存主key"),
    FIELD_URL("URL", "客户活动访问地址"),
    FIELD_ID("ID", "客户活动名称"),
    FIELD_ACTIVITY_ID("ACTIVITY_ID", "公司编码"),
    FIELD_CUSTOMER_ID("CUSTOMER_ID", "公司地址"),
    FIELD_CREATE_TIME("CREATE_TIME", "公司联系人手机"),
    FIELD_START_TIME("START_TIME", "公司联系人"),
    FIELD_END_TIME("END_TIME", "行业编码"),
    FIELD_STATE("STATE", "行业名称");

    private String keyName;

    private String desc;

    CustomerActivityKey(String keyName, String desc) {
        this.keyName = keyName;
        this.desc = desc;
    }

    public String key() {
        return keyName;
    }

}
