package com.tony.demo.microservice.mud.services.order.services.bean

class OrderReq {
    var orderId: String? = null
    var productId: Long = 0
    var userId : Long? = null
    var count: Int = 0
    var price: Double = .0
    var status: Int = 1
    var timestamp: Long = 0
}