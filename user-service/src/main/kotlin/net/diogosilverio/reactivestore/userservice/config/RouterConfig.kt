package net.diogosilverio.reactivestore.userservice.config

import net.diogosilverio.reactivestore.userservice.controller.TransactionController
import net.diogosilverio.reactivestore.userservice.controller.UserController
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse

@Configuration
class RouterConfig(val userController: UserController, val transactionController: TransactionController) {

    @Bean
    fun userRouter(): RouterFunction<ServerResponse> =
        RouterFunctions.route().path("/users") { builder ->
            builder
                .POST(userController::createUser)
                .GET("all", userController::all)
                .GET("{id}", userController::findUserById)
                .PUT("{id}", userController::updateUser)
                .DELETE("{id}", userController::deleteUser)
        }.build()

    @Bean
    fun transactionRouter(): RouterFunction<ServerResponse> =
        RouterFunctions.route().path("/transactions") { builder ->
            builder
                .GET("user/{userId}", transactionController::userTransactions)
                .POST("", transactionController::createTransaction)
        }.build()
}