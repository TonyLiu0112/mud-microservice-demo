package com.tony.demo.microservice.mud.services.user.eventuate.subscriber;

import com.tony.demo.microservice.mud.common.eventuate.domain.Money;
import com.tony.demo.microservice.mud.common.eventuate.event.order.OrderCreatedEvent;
import com.tony.demo.microservice.mud.services.user.eventuate.command.ReserveCreditCommand;
import com.tony.demo.microservice.mud.services.user.eventuate.entity.User;
import io.eventuate.EntityWithIdAndVersion;
import io.eventuate.EventHandlerContext;
import io.eventuate.EventHandlerMethod;
import io.eventuate.EventSubscriber;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@EventSubscriber(id = "orderEventSubscriber")
public class OrderEventSubscriber {

    @EventHandlerMethod
    public CompletableFuture<EntityWithIdAndVersion<User>> reserveCredit(EventHandlerContext<OrderCreatedEvent> ctx) {
        OrderCreatedEvent event = ctx.getEvent();
        Money orderTotal = event.getOrderTotal();
        String customerId = event.getCustomerId();
        String orderId = ctx.getEntityId();

        return ctx.update(User.class, customerId, new ReserveCreditCommand(orderTotal, orderId));
    }

}
