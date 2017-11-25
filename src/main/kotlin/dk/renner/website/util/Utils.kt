package dk.renner.website.util

import com.rabbitmq.client.AMQP
import java.util.*

fun <T> T.log(prefix: String = ""): T = this.also { println(prefix + it) }

fun amqpPropertyBuilder() = AMQP.BasicProperties.Builder()

fun uuid() = UUID.randomUUID().toString()