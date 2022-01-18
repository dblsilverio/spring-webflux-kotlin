package net.diogosilverio.reactive.productservice.service

import net.diogosilverio.reactive.productservice.dto.ProductDTO
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class DataSetupService(val productService: ProductService) : CommandLineRunner {

    override fun run(vararg args: String?) {
        val p1 = ProductDTO(description = "4K TV", price = 1000.0)
        val p2 = ProductDTO(description = "DSLR Camera", price = 500.0)
        val p3 = ProductDTO(description = "IPhone 13", price = 899.0)
        val p4 = ProductDTO(description = "Headset", price = 170.0)

        Flux.just(p1, p2, p3, p4)
            .flatMap { productService.saveProduct(Mono.just(it)) }
            .subscribe(::println)
    }
}