package dk.renner.website.control

import com.rabbitmq.client.Channel
import com.rabbitmq.client.QueueingConsumer
import dk.renner.website.config.RabbitMqProperties
import dk.renner.website.util.logFor
import dk.renner.website.util.rabbitMqMessageProperties
import dk.renner.website.util.timestampNow
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class RabbitMqConsumerScheduler(val rabbitMqProperties: RabbitMqProperties, val rabbitMqChannel: Channel) {
    val log = logFor<RabbitMqConsumerScheduler>()
    val consumer: QueueingConsumer = QueueingConsumer(rabbitMqChannel).also {
        rabbitMqChannel.basicConsume(rabbitMqProperties.queue, false, it)
    }

    @Scheduled(fixedDelay = 10)
    fun listen() {
        val delivery = consumer.nextDelivery() // blocking
        val deliveryProps = delivery.properties
        val replyProps = rabbitMqMessageProperties {
            correlationId(deliveryProps.correlationId)
            timestampNow()
        }

        val message = String(delivery.body).also { log.info("recieved message from producer '$it'") }
        val reply = message.reversed()

        rabbitMqChannel.basicPublish("", deliveryProps.replyTo, replyProps, reply.toByteArray())
        rabbitMqChannel.basicAck(delivery.envelope.deliveryTag, false)
    }
}