package dk.renner.rpc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class RabbitMqRpcApplication

fun main(args: Array<String>) {
    runApplication<RabbitMqRpcApplication>(*args)
}

