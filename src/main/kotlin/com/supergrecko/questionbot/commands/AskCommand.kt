package com.supergrecko.questionbot.commands

import me.aberrantfox.kjdautils.api.dsl.CommandSet
import me.aberrantfox.kjdautils.api.dsl.commands
import me.aberrantfox.kjdautils.internal.arguments.SentenceArg
import me.aberrantfox.kjdautils.internal.arguments.TextChannelArg

@CommandSet("core")
fun askCommand() = commands {
    command("ask") {
        description = "Ask the channel a question."
        requiresGuild = true
        expect(TextChannelArg, SentenceArg)

        execute {
            val (id, question) = it.args

            // TODO: Implement
            it.respond("Id: $id, Question: $question")
        }
    }
}
