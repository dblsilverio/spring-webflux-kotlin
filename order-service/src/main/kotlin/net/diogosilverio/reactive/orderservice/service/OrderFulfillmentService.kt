package net.diogosilverio.reactive.orderservice.service

import net.diogosilverio.reactive.orderservice.client.ProductClient
import net.diogosilverio.reactive.orderservice.client.UserClient
import net.diogosilverio.reactive.orderservice.dto.PurchaseOrderRequestDTO
import net.diogosilverio.reactive.orderservice.dto.PurchaseOrderResponseDTO
import net.diogosilverio.reactive.orderservice.dto.RequestContext
import net.diogosilverio.reactive.orderservice.repository.PurchaseOrderRepository
import net.diogosilverio.reactive.orderservice.util.EntityDtoUtil.ContextHelper
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import reactor.util.retry.Retry.fixedDelay
import java.time.Duration.ofSeconds

@Service
class OrderFulfillmentService(
    val productClient: ProductClient,
    val userClient: UserClient,
    val orderRepository: PurchaseOrderRepository
) {

    fun processOrder(purchaseOrderRequestDTO: Mono<PurchaseOrderRequestDTO>): Mono<PurchaseOrderResponseDTO> {
        return purchaseOrderRequestDTO.map { RequestContext(purchaseOrderRequestDTO = it) }
            .flatMap(this::requestProduct)
            .doOnNext(ContextHelper::setTransactionDto)
            .flatMap(this::requestUser)
            .map(ContextHelper::getPurchaseOrder)
            .map { ContextHelper.getPurchaseOrderDto(orderRepository.save(it)) }
            .subscribeOn(Schedulers.boundedElastic())

    }

    private fun requestProduct(requestContext: RequestContext): Mono<RequestContext> =
        productClient.getProductById(requestContext.purchaseOrderRequestDTO.productId)
            .doOnNext { requestContext.productDTO = it }
            .retryWhen(fixedDelay(3, ofSeconds(1)))
            .thenReturn(requestContext)

    private fun requestUser(requestContext: RequestContext): Mono<RequestContext> =
        userClient.authorizeTransaction(requestContext.transactionRequestDTO!!)
            .doOnNext { requestContext.transactionResponseDTO = it }
            .thenReturn(requestContext)

}