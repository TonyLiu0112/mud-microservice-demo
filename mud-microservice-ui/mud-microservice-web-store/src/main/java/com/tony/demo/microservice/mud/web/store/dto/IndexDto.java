package com.tony.demo.microservice.mud.web.store.dto;

/**
 * Created by Tony on 07/04/2017.
 */
public class IndexDto {

    private String customerName;

    private String activityName;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
}
