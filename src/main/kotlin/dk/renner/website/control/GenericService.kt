package dk.renner.website.control

import dk.renner.website.config.RabbitMqProperties
import org.springframework.stereotype.Service

@Service
class GenericService(val rabbitMqProperties: RabbitMqProperties) {

    fun doStuff() {
        println("hello ${rabbitMqProperties.user}")
    }

}