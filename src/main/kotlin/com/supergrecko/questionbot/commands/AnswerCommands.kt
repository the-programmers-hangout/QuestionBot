package com.supergrecko.questionbot.commands

import com.supergrecko.questionbot.arguments.QuestionArg
import com.supergrecko.questionbot.dataclasses.BotConfig
import me.aberrantfox.kjdautils.api.dsl.CommandSet
import me.aberrantfox.kjdautils.api.dsl.commands
import me.aberrantfox.kjdautils.internal.arguments.MessageArg
import me.aberrantfox.kjdautils.internal.arguments.SentenceArg

@CommandSet("core")
fun answerCommands(config: BotConfig) = commands {
    command("reply") {
        description = "Reply to a question"
        requiresGuild = true

        expect(QuestionArg, SentenceArg)

        execute {

        }
    }

    command("delanswer") {
        description = "Delete an answer from a question."
        requiresGuild = true

        expect(MessageArg)

        execute {

        }
    }

    command("addanswer") {
        description = "Manually add an already existing message as a reply to a question"
        requiresGuild = true

        expect(MessageArg, QuestionArg)

        execute {

        }
    }
}
