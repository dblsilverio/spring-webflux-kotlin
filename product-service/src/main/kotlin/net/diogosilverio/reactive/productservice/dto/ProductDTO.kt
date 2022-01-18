package net.diogosilverio.reactive.productservice.dto

data class ProductDTO(
    val id: String?,
    val description: String,
    val price: Double
) {
    constructor(description: String, price: Double) : this(description = description, price = price, id = null)
}