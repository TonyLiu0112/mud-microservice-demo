package com.tony.demo.microservice.mud.services.order.services

import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import com.tony.demo.microservice.mud.common.eventuate.domain.Money
import com.tony.demo.microservice.mud.services.order.eventuate.command.CreateOrderCommand
import com.tony.demo.microservice.mud.services.order.eventuate.command.OrderCommand
import com.tony.demo.microservice.mud.services.order.eventuate.entity.Order
import com.tony.demo.microservice.mud.services.order.services.bean.OrderReq
import io.eventuate.sync.AggregateRepository
import org.bson.Document
import org.springframework.stereotype.Service
import java.math.BigDecimal

/**
 * Simple order services that just mock.
 */
@Service
class OrderService(val mongoDatabase: MongoDatabase,
                   val repository: AggregateRepository<Order, OrderCommand>) {

    fun getOrders(userId: Long): MutableList<Any>? = mongoDatabase.getCollection("orders").find().toMutableList()

    fun addOrder(orderReq: OrderReq) {
        val doc = Document("orderId", orderReq.orderId)
                .append("productId", orderReq.productId)
                .append("userId", orderReq.userId)
                .append("count", orderReq.count)
                .append("timestamp", orderReq.timestamp)
                .append("price", orderReq.price)
                .append("status", orderReq.status)
        mongoDatabase.getCollection("orders").insertOne(doc)
    }

    fun confirmOrder(orderReq: OrderReq) = {
        mongoDatabase.getCollection("orders").updateOne(Filters.eq("orderId", orderReq.orderId), Document("status", orderReq.status))
    }

    /**
     * 创建一个订单
     */
    fun createOrder4Eventuate(orderReq: OrderReq) {
        repository.save(CreateOrderCommand(orderReq.userId.toString(), Money(BigDecimal(100))))
    }

}