package net.diogosilverio.reactive.productservice.controller

import net.diogosilverio.reactive.productservice.dto.ProductDTO
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class ProductStreamController(private val flux: Flux<ProductDTO>) {

    fun getProductUpdate(serverRequest: ServerRequest): Mono<ServerResponse> = ServerResponse.ok()
        .contentType(MediaType.TEXT_EVENT_STREAM).body(flux, ProductDTO::class.java)

}