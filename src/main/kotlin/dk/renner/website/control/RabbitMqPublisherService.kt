package dk.renner.website.control

import com.rabbitmq.client.Channel
import com.rabbitmq.client.QueueingConsumer
import dk.renner.website.config.RabbitMqProperties
import dk.renner.website.util.amqpPropertyBuilder
import dk.renner.website.util.uuid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rabbitmq")
class RabbitMqPublisherService(val rabbitMqProperties: RabbitMqProperties, val rabbitMqChannel: Channel) {

    @GetMapping("/publish")
    fun publish(@RequestParam message: String): String {
        // required properties to respond to publish
        val correlationId = uuid()
        val replyQueueName = rabbitMqChannel
                .queueDeclare("${rabbitMqProperties.replyQueuePrefix}.$correlationId", false, true, true, null)
                .queue

        // publish message
        val properties = amqpPropertyBuilder()
                .correlationId(correlationId)
                .replyTo(replyQueueName)
                .build()
        rabbitMqChannel.basicPublish(rabbitMqProperties.exchange, rabbitMqProperties.routingKey, properties, message.toByteArray())

        // consume response
        val consumer = QueueingConsumer(rabbitMqChannel)
        rabbitMqChannel.basicConsume(replyQueueName, true, consumer)
        return String(consumer.nextDelivery().body)
    }

}