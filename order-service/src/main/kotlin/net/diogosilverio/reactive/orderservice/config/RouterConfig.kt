package net.diogosilverio.reactive.orderservice.config

import net.diogosilverio.reactive.orderservice.controller.OrderController
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunctions

@Configuration
class RouterConfig(val orderController: OrderController) {

    @Bean
    fun orderRoutes() = RouterFunctions.route().path("/orders") { builder ->
        builder
            .POST(orderController::createOrder)
            .GET("/{userId}", orderController::getUserOrders)
    }.build()

}