package dk.renner.rpc.control

import com.rabbitmq.client.Channel
import com.rabbitmq.client.QueueingConsumer
import dk.renner.rpc.config.RabbitMqProperties
import dk.renner.rpc.rpc.RpcType
import dk.renner.rpc.util.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/rpc")
class RpcDispatchController(val rabbitMqProperties: RabbitMqProperties, val rabbitMqChannel: Channel) {
    val log = logFor<RpcDispatchController>()

    @GetMapping("/call")
    fun call(@RequestParam type: String, @RequestParam input: String): String {
        // figure out the rpc-type
        val rpcMethod = RpcType.fromString(type) ?: return "unknown type '$type'"

        // required properties for consumer to reply
        val correlationId = uuid()
        val replyQueue = rabbitMqChannel.replyQueueDeclare(rabbitMqProperties.replyQueuePrefix, correlationId)
        val props = rabbitMqMessageProperties {
            correlationId(correlationId)
            timestampNow()
            replyTo(replyQueue)
        }

        // dispatch rpc-call
        val routingKey = "${rabbitMqProperties.routingKeyPrefix}.${rpcMethod.simpleName}"
        rabbitMqChannel.basicPublish(rabbitMqProperties.exchange, routingKey, props, input.toByteArray())

        // wait for rpc-reply
        val consumer = QueueingConsumer(rabbitMqChannel)
        rabbitMqChannel.basicConsume(replyQueue, true, consumer)
        val delivery = consumer.nextDelivery(rabbitMqProperties.rpcTimeout) // blocking

        return when {
            delivery == null ->
                "timed out waiting for reply for correlation-id '$correlationId'".also(log::error)
            delivery.properties.correlationId != correlationId ->
                "correlation-id's don't match '${delivery.properties.correlationId}' != '$correlationId'".also(log::error)
            else ->
                String(delivery.body).also { log.info("received reply for rpc-type '$rpcMethod' with output '$it'") }
        }
    }
}