package net.diogosilverio.reactive.productservice.repository

import net.diogosilverio.reactive.productservice.entity.Product
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface ProductRepository : ReactiveMongoRepository<Product, String> {

    fun findByPriceBetween(min: Double, max: Double): Flux<Product>

}