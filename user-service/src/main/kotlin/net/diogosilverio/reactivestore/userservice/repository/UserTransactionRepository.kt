package net.diogosilverio.reactivestore.userservice.repository

import net.diogosilverio.reactivestore.userservice.entity.UserTransaction
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface UserTransactionRepository : ReactiveCrudRepository<UserTransaction, Int> {
    fun findByUserId(userId: Int): Flux<UserTransaction>
}