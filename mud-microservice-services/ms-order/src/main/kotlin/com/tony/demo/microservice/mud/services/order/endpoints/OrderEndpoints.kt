package com.tony.demo.microservice.mud.services.order.endpoints

import com.tony.demo.microservice.mud.services.order.services.OrderService
import com.tony.demo.microservice.mud.services.order.services.bean.OrderReq
import com.wrench.utils.restfulapi.response.RestfulBuilder
import com.wrench.utils.restfulapi.response.RestfulResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.web.bind.annotation.*

@RestController
class OrderEndpoints(val orderService: OrderService) : JwtBaseEndPoint() {

    private val logger: Logger = LoggerFactory.getLogger(OrderEndpoints::class.java)

    @GetMapping("orders")
    fun getOrders(oAuth2Authentication: OAuth2Authentication): ResponseEntity<RestfulResponse<Any>>? {
        return try {
            val user = getUser(oAuth2Authentication)
            RestfulBuilder.ok(orderService.getOrders(user.id))
        } catch (e: Exception) {
            logger.error("Failed to find all orders: ${e.message}")
            RestfulBuilder.serverError()
        }
    }

    @PostMapping("orders")
    fun addOrders(@RequestBody orderReq: OrderReq): ResponseEntity<RestfulResponse<Any>>? {
        return try {
            orderService.addOrder(orderReq)
            RestfulBuilder.created()
        } catch (e: Exception) {
            logger.error("Failed to add new order: ${e.message}")
            RestfulBuilder.serverError()
        }
    }

    @PutMapping("orders/confirm")
    fun confirm(@RequestBody orderReq: OrderReq): ResponseEntity<RestfulResponse<Any>>? {
        return try {
            orderService.confirmOrder(orderReq)
            RestfulBuilder.created()
        } catch (e: Exception) {
            logger.error("Failed to confirm order: ${e.message}")
            RestfulBuilder.serverError()
        }
    }

}