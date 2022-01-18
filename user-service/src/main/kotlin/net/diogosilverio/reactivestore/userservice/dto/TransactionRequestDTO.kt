package net.diogosilverio.reactivestore.userservice.dto

data class TransactionRequestDTO(
    val userId: Int,
    val amount: Double
)
