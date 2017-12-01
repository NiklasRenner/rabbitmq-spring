package dk.renner.rpc.rpc

import org.springframework.stereotype.Component
import java.security.MessageDigest
import java.util.*

@Component
class RpcHandlers(val sha512: MessageDigest, val base64Encoder: Base64.Encoder) {
    fun reverse(string: String) = string.reversed()

    fun double(string: String) = string + string

    fun sha512(string: String) = String(base64Encoder.encode(sha512.digest(string.toByteArray())))
}