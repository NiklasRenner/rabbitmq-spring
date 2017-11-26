package dk.renner.rpc.rpc

import org.springframework.stereotype.Component

@Component
class RpcHandlers {
    fun reverse(string: String) = string.reversed()

    fun double(string: String) = string + string
}