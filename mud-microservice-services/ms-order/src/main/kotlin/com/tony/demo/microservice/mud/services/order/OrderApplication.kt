package com.tony.demo.microservice.mud.services.order

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer

@SpringBootApplication
@EnableDiscoveryClient
@EnableResourceServer
class OrderApplication

fun main(args: Array<String>) {
    SpringApplication.run(OrderApplication::class.java, *args)
}