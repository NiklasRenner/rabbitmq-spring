package dk.renner.rpc

import dk.renner.rpc.rpc.*
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import javax.annotation.PostConstruct

@SpringBootApplication
class RabbitMqRpcApplication(val rpcReceiver: RpcReceiver, val rpcHandlers: RpcHandlers) {
    @PostConstruct
    fun startListeners() = listOf(
            Pair(RpcType.DOUBLE, rpcHandlers::double),
            Pair(RpcType.REVERSE, rpcHandlers::reverse),
            Pair(RpcType.SHA512, rpcHandlers::sha512)
    ).forEach {
        rpcReceiver.startListener(it.first, it.second)
    }
}

fun main(args: Array<String>) {
    runApplication<RabbitMqRpcApplication>(*args)
}

