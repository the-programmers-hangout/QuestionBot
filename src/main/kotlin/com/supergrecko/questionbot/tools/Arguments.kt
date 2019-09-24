package com.supergrecko.questionbot.tools

open class Arguments(val args: List<Any?>) {

    /**
     * Get an item from the argument list as type T.
     *
     * If item is not T or is null then `onError` will be ran and the final
     * cast will always fail.
     *
     * @param index the index in the list to grab from
     * @param onError the error handler if item isn't there
     */
    inline fun <reified T> asType(index: Int, onError: () -> Unit = {}): T {
        val arg = args.getOrNull(index)

        // If the item is not null and is of type T return
        if (arg != null && arg is T) {
            return arg
        }

        onError()
        // This will always fail which is why onError() should deal with errors
        return arg as T
    }

    /**
     * Pick a couple indexes from a List<*> inside the args.
     *
     * @param index index in args where List<*> is.
     * @param indexes the indexes in the List<*> to pick from
     * @param onError error handler if any of the items are null
     */
    inline fun <reified T> fromList(index: Int, vararg indexes: Int, onError: (i: Any?) -> Unit = {}): List<T?> {
        // Grab each item from the selected `indexes` and cast all of them to T?
        val result = indexes.map { item ->
            val matches = args.getOrNull(index) as? List<*>
            matches?.getOrNull(item) as? T
        }

        // Run error lambda on each null item
        result.filter { it == null }.forEach { item -> onError(item) }

        return result
    }

}
