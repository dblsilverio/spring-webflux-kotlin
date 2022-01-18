package net.diogosilverio.reactive.orderservice.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClients {

    @Value("\${product.service.url}")
    lateinit var productUrl: String

    @Value("\${user.service.url}")
    lateinit var userUrl: String

    @Bean
    fun productWebClient(): WebClient = WebClient.builder().baseUrl(productUrl).build()

    @Bean
    fun userWebClient(): WebClient = WebClient.builder().baseUrl(userUrl).build()

}