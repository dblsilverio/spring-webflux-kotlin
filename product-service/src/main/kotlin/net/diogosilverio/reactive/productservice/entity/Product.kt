package net.diogosilverio.reactive.productservice.entity

import org.springframework.data.annotation.Id

data class Product(
    @Id
    var id: String?,
    var description: String,
    var price: Double
) {
}
