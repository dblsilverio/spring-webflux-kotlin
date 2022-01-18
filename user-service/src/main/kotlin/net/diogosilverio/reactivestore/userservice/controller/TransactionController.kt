package net.diogosilverio.reactivestore.userservice.controller

import net.diogosilverio.reactivestore.userservice.dto.TransactionRequestDTO
import net.diogosilverio.reactivestore.userservice.dto.TransactionResponseDTO
import net.diogosilverio.reactivestore.userservice.entity.UserTransaction
import net.diogosilverio.reactivestore.userservice.service.TransactionService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class TransactionController(val transactionService: TransactionService) {

    fun createTransaction(serverRequest: ServerRequest): Mono<ServerResponse> =
        serverRequest.bodyToMono(TransactionRequestDTO::class.java)
            .map(transactionService::createTransaction)
            .flatMap { ServerResponse.ok().body(it, TransactionResponseDTO::class.java) }

    fun userTransactions(serverRequest: ServerRequest): Mono<ServerResponse> {
        val transactions = transactionService.userTransactions(serverRequest.pathVariable("userId").toInt())

        return transactions.hasElements().flatMap {
            if (it) ServerResponse.ok().body(transactions, UserTransaction::class.java)
            else ServerResponse.noContent().build()
        }
    }
}