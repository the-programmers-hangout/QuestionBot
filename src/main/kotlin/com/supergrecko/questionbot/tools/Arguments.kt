package com.supergrecko.questionbot.tools

open class Arguments(val args: List<Any?>) {

    inline fun <reified T> asType(index: Int, onError: () -> Unit = {}): T {
        val arg = args.getOrNull(index)

        if (arg != null && arg is T) {
            return arg
        }

        onError()
        // This will always fail which is why default() should deal with errors
        return arg as T
    }

    inline fun <reified T> fromList(index: Int, vararg indexes: Int): List<T?> {
        return indexes.map { (args.getOrNull(index) as? List<*>)?.getOrNull(it) as? T }
    }

}
