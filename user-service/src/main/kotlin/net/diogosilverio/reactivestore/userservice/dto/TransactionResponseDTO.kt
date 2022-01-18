package net.diogosilverio.reactivestore.userservice.dto

data class TransactionResponseDTO(
    val userId: Int,
    val amount: Double,
    val status: TransactionStatus
)
