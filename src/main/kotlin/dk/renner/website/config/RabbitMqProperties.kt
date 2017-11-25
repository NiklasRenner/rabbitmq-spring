package dk.renner.website.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("rabbitmq")
class RabbitMqProperties{
    lateinit var user: String
    lateinit var password: String
    lateinit var host: String
    lateinit var exchange: String
    lateinit var queue: String
    lateinit var routingKey: String
}