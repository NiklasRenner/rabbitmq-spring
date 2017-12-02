package dk.renner.rpc.config

import com.rabbitmq.client.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import java.security.MessageDigest
import java.util.*

@Configuration
@EnableAsync
class KotlinConfig {
    @Bean
    fun sha512MessageDigest(): MessageDigest = MessageDigest.getInstance("SHA-256")

    @Bean
    fun base64Encoder(): Base64.Encoder = Base64.getEncoder()

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