package dk.renner.rpc.rpc

import com.rabbitmq.client.Channel
import com.rabbitmq.client.QueueingConsumer
import dk.renner.rpc.config.RabbitMqProperties
import dk.renner.rpc.util.*
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class RpcReceiver(val rabbitMqProperties: RabbitMqProperties, val rabbitMqChannel: Channel, val rpcHandlers: RpcHandlers) {
    val log = logFor<RpcReceiver>()

    @Async
    fun startListener(rpcType: RpcType, rpcHandler: (String) -> String) {
        val consumer = QueueingConsumer(rabbitMqChannel)
        rabbitMqChannel.basicConsume("${rabbitMqProperties.queuePrefix}.${rpcType.simpleName}", false, consumer)

        while (true) {
            val delivery = consumer.nextDelivery() // blocking
            val deliveryProps = delivery.properties
            val input = String(delivery.body).also { log.info("recieved request for rpc-type '$rpcType' with input '$it'") }

            val output = rpcHandler(input)
            val replyProps = rabbitMqMessageProperties {
                correlationId(deliveryProps.correlationId)
                timestampNow()
            }

            rabbitMqChannel.basicPublish("", deliveryProps.replyTo, replyProps, output.toByteArray())
            rabbitMqChannel.basicAck(delivery.envelope.deliveryTag, false)
        }
    }
}