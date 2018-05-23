package com.tony.demo.microservice.mud.common.eventuate.event.user;

import com.tony.demo.microservice.mud.common.eventuate.event.UserEvent;

public class UserCreditLimitExceededEvent implements UserEvent {

    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
