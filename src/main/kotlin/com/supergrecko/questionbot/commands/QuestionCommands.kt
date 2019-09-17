package com.supergrecko.questionbot.commands

import com.supergrecko.questionbot.arguments.QuestionArg
import com.supergrecko.questionbot.dataclasses.BotConfig
import me.aberrantfox.kjdautils.api.dsl.CommandSet
import me.aberrantfox.kjdautils.api.dsl.commands
import me.aberrantfox.kjdautils.internal.arguments.SentenceArg
import me.aberrantfox.kjdautils.internal.arguments.TextChannelArg

@CommandSet("questions")
fun questionCommands(config: BotConfig) = commands {
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

    command("edit") {
        description = "Edit a question"
        requiresGuild = true

        expect(QuestionArg, SentenceArg)

        execute {

        }
    }

    command("delete") {
        description = "Delete a question"
        requiresGuild = true

        expect(QuestionArg)

        execute {

        }
    }
}
