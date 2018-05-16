package com.tony.demo.microservice.mud.services.order.eventuate.subscriber

import com.tony.demo.microservice.mud.common.eventuate.event.customer.CustomerCreditLimitExceededEvent
import com.tony.demo.microservice.mud.common.eventuate.event.customer.CustomerCreditReservedEvent
import com.tony.demo.microservice.mud.services.order.eventuate.command.ApproveOrderCommand
import com.tony.demo.microservice.mud.services.order.eventuate.command.RejectOrderCommand
import com.tony.demo.microservice.mud.services.order.eventuate.entity.Order
import io.eventuate.EntityWithIdAndVersion
import io.eventuate.EventHandlerContext
import io.eventuate.EventHandlerMethod
import io.eventuate.EventSubscriber
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture

/**
 * 用户事件订阅者，订阅用户的事件信息
 */
@Component
@EventSubscriber(id = "customerEventSubscriber")
class CustomerEventSubscriber {

    @EventHandlerMethod
    fun creditLimitReserved(ctx: EventHandlerContext<CustomerCreditReservedEvent>): CompletableFuture<EntityWithIdAndVersion<Order>> {
        return ctx.update(Order::class.java, ctx.event.orderId, ApproveOrderCommand())
    }

    @EventHandlerMethod
    fun creditLimitExceeded(ctx: EventHandlerContext<CustomerCreditLimitExceededEvent>): CompletableFuture<EntityWithIdAndVersion<Order>> {
        return ctx.update(Order::class.java, ctx.event.orderId, RejectOrderCommand())
    }

}