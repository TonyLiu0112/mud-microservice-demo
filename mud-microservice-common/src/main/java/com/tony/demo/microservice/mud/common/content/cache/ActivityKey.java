package com.tony.demo.microservice.mud.common.content.cache;

/**
 * 活动缓存key描述对象
 * 活动信息已hash结构存储
 * <p>
 * Created by Tony on 07/12/2016.
 */
public enum ActivityKey {

    KEY("ACTIVITY_", "活动hash结构主Key"),
    FIELD_ID("ID", "主键id"),
    FIELD_NAME("NAME", "活动名称"),
    FIELD_CODE("CODE", "活动编码"),
    FIELD_STATE("STATE", "活动状态"),
    FIELD_TYPE("TYPE", "活动类型"),
    FIELD_ACTIVITY_TIME("ACTIVITY_TIME", "活动时间"),
    FIELD_URL("URL", "活动演示地址");


    private String keyName;

    private String desc;

    ActivityKey(String keyName, String desc) {
        this.keyName = keyName;
        this.desc = desc;
    }

    public String key() {
        return keyName;
    }
}
