package net.diogosilverio.reactive.productservice.service

import net.diogosilverio.reactive.productservice.dto.ProductDTO
import net.diogosilverio.reactive.productservice.repository.ProductRepository
import net.diogosilverio.reactive.productservice.util.EntityDTOUtil.Converters
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.Sinks

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val sink: Sinks.Many<ProductDTO>
) {

    fun getAll(): Flux<ProductDTO> = productRepository.findAll().map(Converters::toDTO)

    fun getProductById(id: String) =
        productRepository.findById(id).map(Converters::toDTO)

    fun saveProduct(dto: Mono<ProductDTO>): Mono<ProductDTO> =
        dto.map(Converters::toEntity)
            .flatMap(productRepository::save)
            .map(Converters::toDTO)
            .doOnNext(sink::tryEmitNext)

    fun updateProduct(id: String, dto: Mono<ProductDTO>): Mono<ProductDTO> = productRepository.findById(id)
        .flatMap {
            dto.map(Converters::toEntity)
                .doOnNext { e -> e.id = id }
        }
        .flatMap(productRepository::save)
        .map(Converters::toDTO)

    fun deleteProductById(id: String): Mono<Void> = productRepository.deleteById(id)

    fun findByPriceRange(min: Double, max: Double): Flux<ProductDTO> =
        productRepository.findByPriceBetween(min, max)
            .map(Converters::toDTO)
}
