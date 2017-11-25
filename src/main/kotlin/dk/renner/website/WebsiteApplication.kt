package dk.renner.website

import com.rabbitmq.client.Channel
import dk.renner.website.config.RabbitMqProperties
import dk.renner.website.control.RabbitMqPublisherService
import dk.renner.website.rabbitmq.TestConsumer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import javax.annotation.PostConstruct

@SpringBootApplication
class WebsiteApplication(val rabbitMqProperties: RabbitMqProperties, val rabbitMqChannel: Channel,
                         val rabbitMqPublisherService: RabbitMqPublisherService) {

    @PostConstruct
    fun init(){
        rabbitMqChannel.basicConsume(rabbitMqProperties.queue, true, TestConsumer())
        println("hello ${rabbitMqProperties.user}")
    }

}

fun main(args: Array<String>) {
    runApplication<WebsiteApplication>(*args)
}

fun <T> T.log(prefix: String = ""): T = this.also { println(prefix + it) }

