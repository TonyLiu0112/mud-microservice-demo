package com.tony.demo.microservice.mud.customer.dao.entity;

import javax.persistence.*;

@Entity
@Table(name = "customer_activity_score_attr")
public class CustomerActivityScoreAttrDO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long customerActivityId;

    private String attrName;

    private String attrValue;

    private String description;

    private String sort;

    private Integer df;

    public Integer getDf() {
        return df;
    }

    public void setDf(Integer df) {
        this.df = df;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort == null ? null : sort.trim();
    }
}