package net.diogosilverio.reactive.orderservice.service

import net.diogosilverio.reactive.orderservice.dto.PurchaseOrderResponseDTO
import net.diogosilverio.reactive.orderservice.repository.PurchaseOrderRepository
import net.diogosilverio.reactive.orderservice.util.EntityDtoUtil.ContextHelper
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers

@Service
class OrderQueryService(val purchaseOrderRepository: PurchaseOrderRepository) {

    fun getProductsByUserId(userId: Int): Flux<PurchaseOrderResponseDTO> {
        return Flux.fromStream { purchaseOrderRepository.findByUserId(userId).stream() }
            .map(ContextHelper::getPurchaseOrderDto)
            .subscribeOn(Schedulers.boundedElastic())
    }

}