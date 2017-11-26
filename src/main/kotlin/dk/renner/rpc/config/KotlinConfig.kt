package dk.renner.rpc.config

import com.rabbitmq.client.*
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.*

@Configuration
class KotlinConfig {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    fun rabbitMqConnectionFactory(rabbitMqProperties: RabbitMqProperties) = ConnectionFactory().apply {
        host = rabbitMqProperties.host
        port = rabbitMqProperties.port
        username = rabbitMqProperties.user
        password = rabbitMqProperties.password
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    fun rabbitMqConnection(rabbitMqConnectionFactory: ConnectionFactory): Connection = rabbitMqConnectionFactory.newConnection()

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    fun rabbitMqChannel(rabbitMqConnection: Connection): Channel = rabbitMqConnection.createChannel()

}