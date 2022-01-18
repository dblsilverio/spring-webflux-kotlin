package net.diogosilverio.reactive.orderservice.client

import net.diogosilverio.reactive.orderservice.dto.ProductDTO
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono

@Service
class ProductClient(
    @Qualifier("productWebClient")
    private val webClient: WebClient
) {

    fun getProductById(productId: String): Mono<ProductDTO> {
        return webClient
            .get()
            .uri("{id}", productId)
            .retrieve()
            .bodyToMono()
    }
}