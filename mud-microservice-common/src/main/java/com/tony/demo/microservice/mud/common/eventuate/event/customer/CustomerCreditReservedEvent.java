package com.tony.demo.microservice.mud.common.eventuate.event.customer;

import com.tony.demo.microservice.mud.common.eventuate.domain.Money;
import com.tony.demo.microservice.mud.common.eventuate.event.CustomerEvent;

public class CustomerCreditReservedEvent implements CustomerEvent {

    private String orderId;
    private Money orderTotal;

    public CustomerCreditReservedEvent(String orderId, Money orderTotal) {
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
