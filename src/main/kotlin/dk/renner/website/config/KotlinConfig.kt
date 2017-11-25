package dk.renner.website.config

import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope

@Configuration
class KotlinConfig {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    fun rabbitMqConnectionFactory(rabbitMqProperties: RabbitMqProperties) = ConnectionFactory().apply {
        host = rabbitMqProperties.host
        port = rabbitMqProperties.port.toInt()
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