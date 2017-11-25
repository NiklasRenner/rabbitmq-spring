package dk.renner.website.control

import com.rabbitmq.client.Channel
import com.rabbitmq.client.QueueingConsumer
import dk.renner.website.config.RabbitMqProperties
import dk.renner.website.util.amqpPropertyBuilder
import dk.renner.website.util.log
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class RabbitMqConsumerService(val rabbitMqProperties: RabbitMqProperties, val rabbitMqChannel: Channel) {

    val consumer = QueueingConsumer(rabbitMqChannel).also {
        rabbitMqChannel.basicConsume(rabbitMqProperties.queue, false, it)
    }

    @Scheduled(fixedDelay = 10)
    fun listen() {
        val delivery = consumer.nextDelivery()
        val deliveryProperties = delivery.properties
        val properties = amqpPropertyBuilder()
                .correlationId(deliveryProperties.correlationId)
                .build()

        val message = String(delivery.body).log("got message: ")
        val response = "responding to: $message"

        rabbitMqChannel.basicPublish("", deliveryProperties.replyTo, properties, response.toByteArray())
        rabbitMqChannel.basicAck(delivery.envelope.deliveryTag, false)
    }

}