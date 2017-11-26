package dk.renner.website.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("rabbitmq")
class RabbitMqProperties {
    var user: String = "guest"
    var password: String = "guest"
    var host: String = "localhost"
    var port: String = "5672"
    var exchange: String = "test.exchange"
    var queue: String = "test.queue"
    var replyQueuePrefix: String = "test.queue.reply"
    var routingKey: String = "test.key"
}