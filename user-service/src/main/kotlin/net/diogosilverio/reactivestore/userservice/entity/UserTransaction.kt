package net.diogosilverio.reactivestore.userservice.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("user_transactions")
data class UserTransaction(
    @Id
    var id: Int?,
    var userId: Int,
    var amount: Double,
    var transactionDate: LocalDateTime
)