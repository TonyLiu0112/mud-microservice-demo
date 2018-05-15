package com.tony.demo.microservice.mud.common.eventuate.event;

import io.eventuate.Event;
import io.eventuate.EventEntity;

@EventEntity(entity = "com.tony.demo.microservice.mud.services.customer.eventuate.event.entity.Order")
public interface CustomerEvent extends Event {
}
