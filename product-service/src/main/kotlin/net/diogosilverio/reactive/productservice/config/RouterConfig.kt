package net.diogosilverio.reactive.productservice.config

import net.diogosilverio.reactive.productservice.controller.ProductController
import net.diogosilverio.reactive.productservice.controller.ProductStreamController
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse

@Configuration
class RouterConfig(
    val productController: ProductController,
    val productStreamController: ProductStreamController) {

    @Bean
    fun productsRouterFunction(): RouterFunction<ServerResponse> =
        RouterFunctions.route().path("/products") { builder ->
            builder
                .GET("/all", productController::all)
                .GET("{id:\\w{24}}", productController::getById)
                .GET("/prices/{min}/to/{max}", productController::findByPriceRange)
                .GET("/events", productStreamController::getProductUpdate)
                .POST(productController::saveProduct)
                .PUT("{id:\\w{24}}", productController::updateProduct)
                .DELETE("/{id:\\w{24}}", productController::deleteProduct)
        }.build()

}