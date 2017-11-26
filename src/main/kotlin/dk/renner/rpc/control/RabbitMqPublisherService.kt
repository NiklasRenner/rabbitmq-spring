package dk.renner.rpc.control

import com.rabbitmq.client.Channel
import com.rabbitmq.client.QueueingConsumer
import dk.renner.rpc.config.RabbitMqProperties
import dk.renner.rpc.util.rabbitMqMessageProperties
import dk.renner.rpc.util.logFor
import dk.renner.rpc.util.timestampNow
import dk.renner.rpc.util.uuid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rabbitmq")
class RabbitMqPublisherService(val rabbitMqProperties: RabbitMqProperties, val rabbitMqChannel: Channel) {
    val log = logFor<RabbitMqPublisherService>()

    @GetMapping("/rpc")
    fun rpc(@RequestParam message: String): String {
        // required properties for consumer to reply
        val correlationId = uuid()
        val replyQueueName = rabbitMqChannel
                .queueDeclare("${rabbitMqProperties.replyQueuePrefix}.$correlationId", false, true, true, null)
                .queue

        // publish message
        val props = rabbitMqMessageProperties {
            correlationId(correlationId)
            timestampNow()
            replyTo(replyQueueName)
        }
        rabbitMqChannel.basicPublish(rabbitMqProperties.exchange, rabbitMqProperties.routingKey, props, message.toByteArray())

        // consume response
        val consumer = QueueingConsumer(rabbitMqChannel)
        rabbitMqChannel.basicConsume(replyQueueName, true, consumer)
        val delivery = consumer.nextDelivery(rabbitMqProperties.rpcTimeout) // blocking

        return when {
            delivery == null ->
                "timed out waiting for response for correlation-id '$correlationId'".also(log::error)
            delivery.properties.correlationId != correlationId ->
                "correlation-id's don't match '${delivery.properties.correlationId}' != '$correlationId'".also(log::error)
            else ->
                String(delivery.body).also { log.info("received message from rpc-consumer '$it'") }
        }
    }
}