package net.diogosilverio.reactive.orderservice.controller

import net.diogosilverio.reactive.orderservice.dto.OrderStatus.COMPLETED
import net.diogosilverio.reactive.orderservice.dto.PurchaseOrderRequestDTO
import net.diogosilverio.reactive.orderservice.dto.PurchaseOrderResponseDTO
import net.diogosilverio.reactive.orderservice.service.OrderFulfillmentService
import net.diogosilverio.reactive.orderservice.service.OrderQueryService
import org.springframework.http.HttpStatus.BAD_GATEWAY
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClientRequestException
import org.springframework.web.reactive.function.client.WebClientResponseException
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class OrderController(
    private val orderFulfillmentService: OrderFulfillmentService,
    private val orderQueryService: OrderQueryService
) {

    fun createOrder(serverRequest: ServerRequest): Mono<ServerResponse> =
        orderFulfillmentService.processOrder(serverRequest.bodyToMono(PurchaseOrderRequestDTO::class.java))
            .flatMap {
                if (it.status == COMPLETED) ServerResponse.ok().bodyValue(it)
                else ServerResponse.status(400).bodyValue(it)
            }
            .onErrorReturn(WebClientResponseException::class.java, ServerResponse.badRequest().build().block()!!)
            .onErrorReturn(WebClientRequestException::class.java,ServerResponse.status(BAD_GATEWAY).build().block()!! )

    fun getUserOrders(serverRequest: ServerRequest): Mono<ServerResponse> =
        ServerResponse.ok()
            .body(
                orderQueryService.getProductsByUserId(serverRequest.pathVariable("userId").toInt()),
                PurchaseOrderResponseDTO::class.java
            )


}