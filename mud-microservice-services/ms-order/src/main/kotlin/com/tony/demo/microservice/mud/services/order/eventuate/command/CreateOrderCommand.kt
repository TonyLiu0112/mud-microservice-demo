package com.tony.demo.microservice.mud.services.order.eventuate.command

import com.tony.demo.microservice.mud.common.eventuate.domain.Money


class CreateOrderCommand : OrderCommand {
    var customerId : String? = null
    var money : Money? = null

    constructor()

    constructor(customerId: String, money : Money) {
        this.customerId = customerId
        this.money = money
    }
}