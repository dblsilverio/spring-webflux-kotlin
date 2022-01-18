package net.diogosilverio.reactive.productservice.controller

import net.diogosilverio.reactive.productservice.dto.ProductDTO
import net.diogosilverio.reactive.productservice.service.ProductService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.util.concurrent.ThreadLocalRandom

@Component
class ProductController(val productService: ProductService) {

    fun all(serverRequest: ServerRequest): Mono<ServerResponse> {
        val products = productService.getAll()

        return products.hasElements()
            .flatMap {
                if (it) ServerResponse.ok().body(products, ProductDTO::class.java)
                else ServerResponse.noContent().build()
            }
    }

    fun getById(serverRequest: ServerRequest): Mono<ServerResponse> =
        productService.getProductById(serverRequest.pathVariable("id"))
            .doOnNext { this.simulateRandomException() }
            .flatMap { ServerResponse.ok().bodyValue(it) }
            .defaultIfEmpty(ServerResponse.notFound().build().block()!!)

    fun findByPriceRange(serverRequest: ServerRequest): Mono<ServerResponse> {
        val min = serverRequest.pathVariable("min").toDouble()
        val max = serverRequest.pathVariable("max").toDouble()
        val productsByPrice = productService.findByPriceRange(min, max)

        return productsByPrice.hasElements().flatMap {
            if (it) ServerResponse.ok().body(productsByPrice, ProductDTO::class.java)
            else ServerResponse.noContent().build()
        }
    }

    fun saveProduct(serverRequest: ServerRequest): Mono<ServerResponse> =
        serverRequest.bodyToMono(ProductDTO::class.java)
            .flatMap { dto -> productService.saveProduct(Mono.just(dto)) }
            .flatMap { dto -> ServerResponse.ok().bodyValue(dto) }

    fun updateProduct(serverRequest: ServerRequest): Mono<ServerResponse> =
        serverRequest.bodyToMono(ProductDTO::class.java)
            .flatMap { dto -> productService.updateProduct(serverRequest.pathVariable("id"), Mono.just(dto)) }
            .flatMap { dto -> ServerResponse.ok().bodyValue(dto) }
            .defaultIfEmpty(ServerResponse.notFound().build().block()!!)

    fun deleteProduct(serverRequest: ServerRequest): Mono<ServerResponse> =
        productService.deleteProductById(serverRequest.pathVariable("id"))
            .flatMap { ServerResponse.noContent().build() }

    fun simulateRandomException() {
        if (ThreadLocalRandom.current().nextInt(1, 10) > 5) {
            throw RuntimeException("Intermittent error")
        }
    }
}