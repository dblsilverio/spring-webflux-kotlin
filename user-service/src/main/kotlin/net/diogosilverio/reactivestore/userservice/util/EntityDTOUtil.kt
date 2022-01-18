package net.diogosilverio.reactivestore.userservice.util

import net.diogosilverio.reactivestore.userservice.dto.TransactionRequestDTO
import net.diogosilverio.reactivestore.userservice.dto.TransactionResponseDTO
import net.diogosilverio.reactivestore.userservice.dto.TransactionStatus
import net.diogosilverio.reactivestore.userservice.dto.UserDTO
import net.diogosilverio.reactivestore.userservice.entity.User
import net.diogosilverio.reactivestore.userservice.entity.UserTransaction
import java.time.LocalDateTime

class EntityDTOUtil {

    companion object Converters {
        fun toDTO(user: User): UserDTO = UserDTO(id = user.id!!, name = user.name, balance = user.balance)
        fun toEntity(dto: UserDTO): User = User(name = dto.name, balance = dto.balance)

        fun toDTO(entity: UserTransaction, status: TransactionStatus): TransactionResponseDTO =
            TransactionResponseDTO(userId = entity.userId, amount = entity.amount, status = status)

        fun toDTO(request: TransactionRequestDTO, status: TransactionStatus): TransactionResponseDTO =
            TransactionResponseDTO(userId = request.userId, amount = request.amount, status = status)

        fun toEntity(dto: TransactionRequestDTO): UserTransaction =
            UserTransaction(userId = dto.userId, amount = dto.amount, transactionDate = LocalDateTime.now(), id = null)
    }

}