package net.diogosilverio.reactive.orderservice.dto

data class TransactionResponseDTO(
    val userId: Int,
    val amount: Double,
    val status: TransactionStatus
)
