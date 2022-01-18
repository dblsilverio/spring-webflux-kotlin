package net.diogosilverio.reactive.orderservice.client

import net.diogosilverio.reactive.orderservice.dto.TransactionRequestDTO
import net.diogosilverio.reactive.orderservice.dto.TransactionResponseDTO
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono

@Service
class UserClient(
    @Qualifier("userWebClient")
    private val webClient: WebClient
) {

    fun authorizeTransaction(transactionRequestDTO: TransactionRequestDTO): Mono<TransactionResponseDTO> {
        return webClient
            .post()
            .uri("transactions")
            .bodyValue(transactionRequestDTO)
            .retrieve()
            .bodyToMono()
    }
}