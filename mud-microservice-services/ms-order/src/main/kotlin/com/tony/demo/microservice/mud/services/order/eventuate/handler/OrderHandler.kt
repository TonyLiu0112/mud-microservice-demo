package com.tony.demo.microservice.mud.services.order.eventuate.handler

import com.tony.demo.microservice.mud.common.eventuate.event.customer.CustomerCreditLimitExceededEvent
import com.tony.demo.microservice.mud.common.eventuate.event.customer.CustomerCreditReservedEvent
import com.tony.demo.microservice.mud.services.order.eventuate.command.ApproveOrderCommand
import com.tony.demo.microservice.mud.services.order.eventuate.command.RejectOrderCommand
import com.tony.demo.microservice.mud.services.order.eventuate.event.entity.Order
import io.eventuate.EntityWithIdAndVersion
import io.eventuate.EventHandlerContext
import io.eventuate.EventHandlerMethod
import io.eventuate.EventSubscriber
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture

@Service
@EventSubscriber(id = "orderWorkflow")
class OrderHandler {

    @EventHandlerMethod
    fun creditLimitReserved(ctx: EventHandlerContext<CustomerCreditReservedEvent>): CompletableFuture<EntityWithIdAndVersion<Order>> {
        val orderId = ctx.getEvent().getOrderId()
        return ctx.update(Order::class.java, orderId, ApproveOrderCommand())
    }

    @EventHandlerMethod
    fun creditLimitExceeded(ctx: EventHandlerContext<CustomerCreditLimitExceededEvent>): CompletableFuture<EntityWithIdAndVersion<Order>> {
        val orderId = ctx.getEvent().getOrderId()
        return ctx.update(Order::class.java, orderId, RejectOrderCommand())
    }

}