package com.tony.demo.microservice.mud.services.user.eventuate;


import com.tony.demo.microservice.mud.common.eventuate.domain.Money;

import java.util.HashMap;
import java.util.Map;

public class ReservedCreditTracker {
    private Map<String, Money> creditReservations = new HashMap<>();

    public Money reservedCredit() {
        return creditReservations.values().stream().reduce(Money.ZERO, Money::add);
    }

    public void addReservation(String orderId, Money orderTotal) {
        creditReservations.put(orderId, orderTotal);
    }
}