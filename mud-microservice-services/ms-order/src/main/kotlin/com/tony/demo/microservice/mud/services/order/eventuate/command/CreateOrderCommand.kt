package com.tony.demo.microservice.mud.services.order.eventuate.command

import com.tony.demo.microservice.mud.common.eventuate.domain.Money


class CreateOrderCommand(customerId: String, money: Money) : OrderCommand {
    var customerId : String? = customerId
    var money : Money? = money

}