package net.diogosilverio.reactive.orderservice.dto

data class PurchaseOrderResponseDTO(
    val orderId: Int,
    val userId: Int,
    val productId: String,
    val amount: Double,
    val status: OrderStatus
)