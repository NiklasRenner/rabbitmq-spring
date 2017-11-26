package dk.renner.rpc

import dk.renner.rpc.rpc.RpcListeners
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import javax.annotation.PostConstruct

@SpringBootApplication
class RabbitMqRpcApplication(val rpcListeners: RpcListeners) {
    @PostConstruct
    fun startListeners() {
        rpcListeners.startRpcListenerDouble()
        rpcListeners.startRpcListenerReverse()
    }
}

fun main(args: Array<String>) {
    runApplication<RabbitMqRpcApplication>(*args)
}

