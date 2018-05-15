package com.tony.demo.microservice.mud.common.eventuate.event.order;

import com.tony.demo.microservice.mud.common.eventuate.domain.Money;
import com.tony.demo.microservice.mud.common.eventuate.event.OrderEvent;

public class OrderCreatedEvent implements OrderEvent {

    private Money orderTotal;
    private String customerId;

    public OrderCreatedEvent(Money orderTotal, String customerId) {
        this.orderTotal = orderTotal;
        this.customerId = customerId;
    }

    public Money getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Money orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
