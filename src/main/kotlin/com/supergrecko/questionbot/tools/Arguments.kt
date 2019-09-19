package com.supergrecko.questionbot.tools

open class Arguments(val args: List<Any?>) {

    inline fun <reified T> asType(index: Int): T? {
        return args.getOrNull(index) as? T
    }

    inline fun <reified T> fromList(index: Int, vararg indexes: Int): List<T?> {
        return indexes.map { (args.getOrNull(index) as? List<*>)?.getOrNull(it) as? T }
    }

}
