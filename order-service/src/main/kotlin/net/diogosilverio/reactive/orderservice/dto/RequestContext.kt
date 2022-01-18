package net.diogosilverio.reactive.orderservice.dto

data class RequestContext(
    val purchaseOrderRequestDTO: PurchaseOrderRequestDTO,
    var productDTO: ProductDTO?,
    var transactionRequestDTO: TransactionRequestDTO?,
    var transactionResponseDTO: TransactionResponseDTO?
) {
    constructor(purchaseOrderRequestDTO: PurchaseOrderRequestDTO) : this(purchaseOrderRequestDTO, null, null, null)
}
