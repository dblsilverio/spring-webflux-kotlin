package net.diogosilverio.reactivestore.userservice.service

import net.diogosilverio.reactivestore.userservice.dto.TransactionRequestDTO
import net.diogosilverio.reactivestore.userservice.dto.TransactionResponseDTO
import net.diogosilverio.reactivestore.userservice.dto.TransactionStatus.APPROVED
import net.diogosilverio.reactivestore.userservice.dto.TransactionStatus.DECLINED
import net.diogosilverio.reactivestore.userservice.entity.UserTransaction
import net.diogosilverio.reactivestore.userservice.repository.UserRepository
import net.diogosilverio.reactivestore.userservice.repository.UserTransactionRepository
import net.diogosilverio.reactivestore.userservice.util.EntityDTOUtil.Converters
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class TransactionService(val userRepository: UserRepository, val userTransactionRepository: UserTransactionRepository) {

    fun createTransaction(request: TransactionRequestDTO): Mono<TransactionResponseDTO> =
        userRepository.updateUserBalance(userId = request.userId, amount = request.amount)
            .filter { b -> b }
            .map { Converters.toEntity(request) }
            .flatMap(userTransactionRepository::save)
            .map { Converters.toDTO(it, APPROVED) }
            .defaultIfEmpty(Converters.toDTO(request, DECLINED))

    fun userTransactions(userId: Int): Flux<UserTransaction> =
        userTransactionRepository.findByUserId(userId)
}