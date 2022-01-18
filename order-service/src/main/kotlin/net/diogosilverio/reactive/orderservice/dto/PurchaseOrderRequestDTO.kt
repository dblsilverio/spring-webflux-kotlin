package net.diogosilverio.reactive.orderservice.dto

data class PurchaseOrderRequestDTO(
    val userId: Int,
    val productId: String,
)