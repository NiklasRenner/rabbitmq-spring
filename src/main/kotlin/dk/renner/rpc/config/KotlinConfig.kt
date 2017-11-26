package dk.renner.rpc.config

import com.rabbitmq.client.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync

@Configuration
@EnableAsync
class KotlinConfig {
    @Bean
    fun rabbitMqConnectionFactory(rabbitMqProperties: RabbitMqProperties) = ConnectionFactory().apply {
        host = rabbitMqProperties.host
        port = rabbitMqProperties.port
        username = rabbitMqProperties.user
        password = rabbitMqProperties.password
    }

    @Bean
    fun rabbitMqConnection(rabbitMqConnectionFactory: ConnectionFactory): Connection = rabbitMqConnectionFactory.newConnection()

    @Bean
    fun rabbitMqChannel(rabbitMqConnection: Connection): Channel = rabbitMqConnection.createChannel()
}