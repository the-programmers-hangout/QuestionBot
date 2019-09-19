package com.supergrecko.questionbot.extensions

inline fun <reified T> List<*>.at(index: Int): T? {
    return this.getOrNull(index) as T?
}

inline fun <reified T> List<*>.atOrNull(vararg indexes: Int) {

}
