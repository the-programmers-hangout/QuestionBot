package com.supergrecko.questionbot

import me.aberrantfox.kjdautils.api.dsl.PrefixDeleteMode
import me.aberrantfox.kjdautils.api.startBot
import java.lang.IllegalArgumentException

fun main(args: Array<String>) {
    val token = args.firstOrNull() ?: throw IllegalArgumentException("No bot token provided.")

    startBot(token) {
        configure {
            globalPath = "com.supergrecko.questionbot"

            prefix = "$"
            deleteMode = PrefixDeleteMode.Double

            reactToCommands = false
        }
    }
}
