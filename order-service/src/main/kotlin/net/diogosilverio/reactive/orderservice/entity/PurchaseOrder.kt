package net.diogosilverio.reactive.orderservice.entity

import net.diogosilverio.reactive.orderservice.dto.OrderStatus
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class PurchaseOrder(

    @Id
    @GeneratedValue
    var id: Int? = null,
    val productId: String,
    val userId: Int,
    val amount: Double,
    val status: OrderStatus
)
