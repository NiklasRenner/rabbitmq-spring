package dk.renner.rpc.control

import com.rabbitmq.client.Channel
import com.rabbitmq.client.QueueingConsumer
import dk.renner.rpc.config.RabbitMqProperties
import dk.renner.rpc.rpc.RpcHandler
import dk.renner.rpc.rpc.RpcMethod
import dk.renner.rpc.util.*
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class RabbitMqConsumerScheduler(val rabbitMqProperties: RabbitMqProperties, val rabbitMqChannel: Channel) {
    val log = logFor<RabbitMqConsumerScheduler>()
    val rpcHandler = RpcHandler()

    val consumerDouble: QueueingConsumer = QueueingConsumer(rabbitMqChannel).also {
        rabbitMqChannel.basicConsume("${rabbitMqProperties.queuePrefix}.${RpcMethod.DOUBLE.type}", false, it)
    }

    val consumerReverse: QueueingConsumer = QueueingConsumer(rabbitMqChannel).also {
        rabbitMqChannel.basicConsume("${rabbitMqProperties.queuePrefix}.${RpcMethod.REVERSE.type}", false, it)
    }

    fun listen(consumer: QueueingConsumer, rpcHandle: (String) -> String) {
        val delivery = consumer.nextDelivery() // blocking
        val deliveryProps = delivery.properties
        val message = String(delivery.body).also { log.info("recieved message from producer '$it'") }

        val reply = rpcHandle(message)
        val replyProps = rabbitMqMessageProperties {
            correlationId(deliveryProps.correlationId)
            timestampNow()
        }

        rabbitMqChannel.basicPublish("", deliveryProps.replyTo, replyProps, reply.toByteArray())
        rabbitMqChannel.basicAck(delivery.envelope.deliveryTag, false)
    }

    @Scheduled(fixedDelay = 10)
    fun handleReverse(){
        listen(consumerReverse, rpcHandler::reverse)
    }

    @Scheduled(fixedDelay = 10)
    fun handleDouble(){
        listen(consumerDouble, rpcHandler::double)
    }

}