package com.tony.demo.microservice.mud.services.order.eventuate.event.entity

import com.tony.demo.microservice.mud.common.eventuate.OrderState
import com.tony.demo.microservice.mud.common.eventuate.event.order.OrderApprovedEvent
import com.tony.demo.microservice.mud.common.eventuate.event.order.OrderCreatedEvent
import com.tony.demo.microservice.mud.common.eventuate.event.order.OrderRejectedEvent
import com.tony.demo.microservice.mud.services.order.eventuate.command.ApproveOrderCommand
import com.tony.demo.microservice.mud.services.order.eventuate.command.CreateOrderCommand
import com.tony.demo.microservice.mud.services.order.eventuate.command.OrderCommand
import com.tony.demo.microservice.mud.services.order.eventuate.command.RejectOrderCommand
import io.eventuate.Event
import io.eventuate.EventUtil.events
import io.eventuate.ReflectiveMutableCommandProcessingAggregate

class Order : ReflectiveMutableCommandProcessingAggregate<Order, OrderCommand>() {

    var state: OrderState? = null
    var customerId: String? = null

    fun process(cmd: CreateOrderCommand): MutableList<Event> {
        return events(OrderCreatedEvent(cmd.money, cmd.customerId))
    }

    fun apply(event: OrderCreatedEvent) {
        this.state = OrderState.CREATED
        this.customerId = event.customerId
    }

    fun process(cmd: ApproveOrderCommand): List<Event> {
        return events(OrderApprovedEvent(customerId))
    }

    fun process(cmd: RejectOrderCommand): List<Event> {
        return events(OrderRejectedEvent(customerId))
    }

    fun apply(event: OrderApprovedEvent) {
        this.state = OrderState.APPROVED
    }


    fun apply(event: OrderRejectedEvent) {
        this.state = OrderState.REJECTED
    }

}