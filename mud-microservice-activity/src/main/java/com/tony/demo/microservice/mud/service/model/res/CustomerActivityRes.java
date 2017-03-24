package com.tony.demo.microservice.mud.service.model.res;

import java.util.Date;

/**
 * Created by Tony on 15/02/2017.
 */
public class CustomerActivityRes {
    private Long id;

    private Long customerId;

    private Long activityId;

    private Integer state;

    private Date startTime;

    private Date endTime;

    private Date createTime;

    private Integer df;

    private String url;

    private Integer idSite;

    /**
     * 活动信息
     */
    private ActivityRes activity;

    private CustomerRes customer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getDf() {
        return df;
    }

    public void setDf(Integer df) {
        this.df = df;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getIdSite() {
        return idSite;
    }

    public void setIdSite(Integer idSite) {
        this.idSite = idSite;
    }

    public ActivityRes getActivity() {
        return activity;
    }

    public void setActivity(ActivityRes activity) {
        this.activity = activity;
    }

    public CustomerRes getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerRes customer) {
        this.customer = customer;
    }
}
