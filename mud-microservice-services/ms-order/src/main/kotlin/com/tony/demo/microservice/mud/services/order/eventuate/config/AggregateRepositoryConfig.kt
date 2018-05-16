package com.tony.demo.microservice.mud.services.order.eventuate.config

import com.tony.demo.microservice.mud.services.order.eventuate.command.OrderCommand
import com.tony.demo.microservice.mud.services.order.eventuate.entity.Order
import io.eventuate.javaclient.spring.EnableEventHandlers
import io.eventuate.sync.AggregateRepository
import io.eventuate.sync.EventuateAggregateStore
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableEventHandlers
class AggregateRepositoryConfig {

    @Bean
    fun orderRepository(eventStore: EventuateAggregateStore): AggregateRepository<Order, OrderCommand> {
        return AggregateRepository<Order, OrderCommand>(Order::class.java, eventStore)
    }

}