package com.tony.demo.microservice.mud.customer.services.model.res;

public class CustomerActivityAttrRes {

    private Long id;

    private Long customerActivityId;

    private String attrName;

    private String attrValue;

    private Integer attrType;

    private Integer df;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerActivityId() {
        return customerActivityId;
    }

    public void setCustomerActivityId(Long customerActivityId) {
        this.customerActivityId = customerActivityId;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName == null ? null : attrName.trim();
    }

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue == null ? null : attrValue.trim();
    }

    public Integer getDf() {
        return df;
    }

    public void setDf(Integer df) {
        this.df = df;
    }

    public Integer getAttrType() {
        return attrType;
    }

    public void setAttrType(Integer attrType) {
        this.attrType = attrType;
    }
}