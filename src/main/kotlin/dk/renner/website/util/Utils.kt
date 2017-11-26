package dk.renner.website.util

import com.rabbitmq.client.AMQP
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

// debugging convenience function
fun <T> T.log(prefix: String = ""): T = this.also { println(prefix + it) }

// generic "standard"-library extensions
inline fun <reified T : Any> logFor(): Logger = LoggerFactory.getLogger(T::class.java)

fun uuid() = UUID.randomUUID().toString()

// rabbitmq convenience
typealias RabbitMqMessageProperties = AMQP.BasicProperties
typealias RabbitMqMessagePropertiesBuilder = AMQP.BasicProperties.Builder

fun RabbitMqMessagePropertiesBuilder.timestampNow(): RabbitMqMessagePropertiesBuilder
        = timestamp(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)))

fun rabbitMqMessageProperties(applyTo: RabbitMqMessagePropertiesBuilder.() -> RabbitMqMessagePropertiesBuilder): RabbitMqMessageProperties
        = RabbitMqMessagePropertiesBuilder().applyTo().build()