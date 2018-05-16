package com.tony.demo.microservice.mud.services.order.eventuate.entity

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

/**
 * 订单实体对象
 *
 * 对于entity的解释
 *
 *  A an entity is a business object such as an Order.
 *
 *  An entity is an instance of an EntityType. In Domain-Driven Design terms, an entity is an aggregate.
 *  Each entity has an ID, unique among instances of the same entity type,
 *  which you can choose or allow Eventuate to assign to you.
 *
 *  An entity has a sequence of one or more events.
 *
 *  You can perform the following operations on an entity:
 *
 *  1. Create - Create a new instance of the specified entity type with an initial sequence of events. Or, you can specify a unique ID.
 *  2. Update - Append one or more events to the specified entity. Concurrent updates are handled using optimistic locking.
 *  3. Find - Retrieve the events for the specified entity.
 *
 * Command处理聚合器, 将Command转换成事件, 这是的事件都是指订单相关动作事件
 *
 * 该类所有方法都将被反射调用
 *
 * @author Tony
 */
class Order : ReflectiveMutableCommandProcessingAggregate<Order, OrderCommand>() {

    private var state: OrderState? = null
    private var customerId: String? = null

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