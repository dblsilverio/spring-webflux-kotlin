package net.diogosilverio.reactive.productservice.config

import net.diogosilverio.reactive.productservice.dto.ProductDTO
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks

@Configuration
class SinkConfig {

    @Bean
    fun sink(): Sinks.Many<ProductDTO> = Sinks.many().replay().limit(1)

    @Bean
    fun productBroadcast(sink: Sinks.Many<ProductDTO>): Flux<ProductDTO> = sink.asFlux()

}