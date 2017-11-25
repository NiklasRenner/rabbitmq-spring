package dk.renner.website.control

import com.rabbitmq.client.Channel
import dk.renner.website.config.RabbitMqProperties
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rabbitmq")
class RabbitMqPublisherService(val rabbitMqProperties: RabbitMqProperties, val rabbitMqChannel: Channel) {

    @GetMapping("/publish")
    fun publish(@RequestParam message: String) = "published message: $message".also {
        rabbitMqChannel.basicPublish(rabbitMqProperties.exchange, rabbitMqProperties.routingKey, null, message.toByteArray())
    }

}