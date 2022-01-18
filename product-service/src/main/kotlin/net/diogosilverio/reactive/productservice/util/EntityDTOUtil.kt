package net.diogosilverio.reactive.productservice.util

import net.diogosilverio.reactive.productservice.dto.ProductDTO
import net.diogosilverio.reactive.productservice.entity.Product

class EntityDTOUtil {

    companion object Converters {
        fun toDTO(product: Product) = ProductDTO(id = product.id, description = product.description, price = product.price)
        fun toEntity(dto: ProductDTO) = Product(id = dto.id, description = dto.description, price = dto.price)
    }

}