package net.diogosilverio.reactive.orderservice.repository

import net.diogosilverio.reactive.orderservice.entity.PurchaseOrder
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PurchaseOrderRepository : JpaRepository<PurchaseOrder, Int> {

    fun findByUserId(userId: Int): List<PurchaseOrder>

}