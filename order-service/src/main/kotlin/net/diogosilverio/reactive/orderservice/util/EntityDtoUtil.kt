package net.diogosilverio.reactive.orderservice.util

import net.diogosilverio.reactive.orderservice.dto.*
import net.diogosilverio.reactive.orderservice.dto.TransactionStatus.APPROVED
import net.diogosilverio.reactive.orderservice.entity.PurchaseOrder

class EntityDtoUtil {

    companion object ContextHelper {
        fun setTransactionDto(requestContext: RequestContext) {
            val transactionRequestDTO = TransactionRequestDTO(
                userId = requestContext.purchaseOrderRequestDTO.userId,
                amount = requestContext.productDTO!!.price
            )

            requestContext.transactionRequestDTO = transactionRequestDTO
        }

        fun getPurchaseOrder(requestContext: RequestContext): PurchaseOrder {
            val status = if(requestContext.transactionResponseDTO!!.status == APPROVED) OrderStatus.COMPLETED else OrderStatus.FAILED

            return PurchaseOrder(
                userId = requestContext.purchaseOrderRequestDTO.userId,
                productId = requestContext.purchaseOrderRequestDTO.productId,
                amount = requestContext.productDTO!!.price,
                status = status
            )
        }

        fun getPurchaseOrderDto(purchaseOrder: PurchaseOrder) : PurchaseOrderResponseDTO = PurchaseOrderResponseDTO(
            orderId = purchaseOrder.id!!,
            userId = purchaseOrder.userId,
            productId = purchaseOrder.productId,
            amount = purchaseOrder.amount,
            status = purchaseOrder.status
        )
    }

}