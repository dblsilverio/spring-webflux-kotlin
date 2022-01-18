package net.diogosilverio.reactivestore.userservice.service

import net.diogosilverio.reactivestore.userservice.dto.UserDTO
import net.diogosilverio.reactivestore.userservice.repository.UserRepository
import net.diogosilverio.reactivestore.userservice.util.EntityDTOUtil.Converters
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class UserService(private val userRepository: UserRepository) {

    fun all(): Flux<UserDTO> = userRepository.findAll().map(Converters::toDTO)

    fun findById(id: Int): Mono<UserDTO> = userRepository.findById(id).map(Converters::toDTO)

    fun createUser(dto: Mono<UserDTO>): Mono<UserDTO> =
        dto.map(Converters::toEntity).flatMap(userRepository::save).map(Converters::toDTO)

    fun updateUser(id: Int, dto: Mono<UserDTO>): Mono<UserDTO> =
        userRepository.findById(id).flatMap { dto.map(Converters::toEntity).doOnNext { e -> e.id = id } }
            .flatMap(userRepository::save).map(Converters::toDTO)

    fun deleteUser(id: Int): Mono<Void> = userRepository.deleteById(id)

}