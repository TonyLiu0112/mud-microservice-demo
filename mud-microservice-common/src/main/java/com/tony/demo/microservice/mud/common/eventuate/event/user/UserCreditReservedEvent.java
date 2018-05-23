package com.tony.demo.microservice.mud.common.eventuate.event.user;

import com.tony.demo.microservice.mud.common.eventuate.domain.Money;
import com.tony.demo.microservice.mud.common.eventuate.event.UserEvent;

public class UserCreditReservedEvent implements UserEvent {

    private String orderId;
    private Money orderTotal;

    public UserCreditReservedEvent(String orderId, Money orderTotal) {
        this.orderId = orderId;
        this.orderTotal = orderTotal;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Money getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Money orderTotal) {
        this.orderTotal = orderTotal;
    }
}
