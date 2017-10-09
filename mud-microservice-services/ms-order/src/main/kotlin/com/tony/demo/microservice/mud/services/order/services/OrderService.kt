package com.tony.demo.microservice.mud.services.order.services

import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import com.tony.demo.microservice.mud.services.order.services.bean.OrderReq
import org.bson.Document
import org.springframework.stereotype.Service

/**
 * Simple order services that just mock.
 */
@Service
class OrderService(val mongoDatabase: MongoDatabase) {

    /**
     * query orders for all.
     */
    fun getOrders(userId: Long): MutableList<Any>? {
        val orders = mongoDatabase.getCollection("orders").find()
        return orders.toMutableList()
    }

    /**
     * add a new order.
     */
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

    /**
     * order confirm.
     */
    fun confirmOrder(orderReq: OrderReq) {
        mongoDatabase.getCollection("orders")
                .updateOne(Filters.eq("orderId", orderReq.orderId), Document("status", orderReq.status))
    }

}