package com.tony.demo.microservice.mud.common.eventuate.event.customer;

import com.tony.demo.microservice.mud.common.eventuate.event.CustomerEvent;

public class CustomerCreditLimitExceededEvent implements CustomerEvent {

    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
