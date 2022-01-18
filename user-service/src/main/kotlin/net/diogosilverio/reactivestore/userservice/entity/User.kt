package net.diogosilverio.reactivestore.userservice.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("users")
data class User(
    @Id
    var id: Int?,
    var name: String,
    var balance: Double
) {
    constructor(name: String, balance: Double) : this(name = name, balance = balance, id = null)
}