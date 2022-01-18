package net.diogosilverio.reactivestore.userservice.controller

import net.diogosilverio.reactivestore.userservice.dto.UserDTO
import net.diogosilverio.reactivestore.userservice.service.UserService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class UserController(val userService: UserService) {

    fun all(serverRequest: ServerRequest): Mono<ServerResponse> {
        val users = userService.all()

        return users.hasElements().flatMap {
            if (it) ServerResponse.ok().body(users, UserDTO::class.java)
            else ServerResponse.noContent().build()
        }
    }

    fun findUserById(serverRequest: ServerRequest): Mono<ServerResponse> =
        userService.findById(serverRequest.pathVariable("id").toInt())
            .flatMap { ServerResponse.ok().bodyValue(it) }
            .defaultIfEmpty(ServerResponse.notFound().build().block()!!)

    fun createUser(serverRequest: ServerRequest): Mono<ServerResponse> =
        userService.createUser(serverRequest.bodyToMono(UserDTO::class.java))
            .flatMap { ServerResponse.ok().bodyValue(it) }

    fun updateUser(serverRequest: ServerRequest): Mono<ServerResponse> =
        serverRequest.bodyToMono(UserDTO::class.java)
            .flatMap { userService.updateUser(serverRequest.pathVariable("id").toInt(), Mono.just(it)) }
            .flatMap { ServerResponse.ok().bodyValue(it) }
            .defaultIfEmpty(ServerResponse.notFound().build().block()!!)

    fun deleteUser(serverRequest: ServerRequest): Mono<ServerResponse> =
        userService.deleteUser(serverRequest.pathVariable("id").toInt())
            .flatMap { ServerResponse.noContent().build() }

}