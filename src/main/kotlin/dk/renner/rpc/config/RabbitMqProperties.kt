package dk.renner.rpc.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("rabbitmq")
class RabbitMqProperties {
    var user: String = "guest"
    var password: String = "guest"
    var host: String = "localhost"
    var port: Int = 5672
    var exchange: String = "test.exchange"
    var queuePrefix: String = "test.queue"
    var replyQueuePrefix: String = "test.queue.reply"
    var routingKeyPrefix: String = "test.key"
    var rpcTimeout: Long = 60000
}