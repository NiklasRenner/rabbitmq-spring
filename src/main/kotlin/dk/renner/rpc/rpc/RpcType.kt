package dk.renner.rpc.rpc

enum class RpcType(val simpleName: String) {
    REVERSE("reverse"),
    DOUBLE("double"),
    SHA512("sha512");

    companion object {
        fun fromString(string: String) = RpcType.values().find { it.simpleName == string.toLowerCase() }
    }
}