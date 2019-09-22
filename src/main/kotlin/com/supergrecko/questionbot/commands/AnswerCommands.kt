package com.supergrecko.questionbot.commands

import com.supergrecko.questionbot.arguments.QuestionArg
import com.supergrecko.questionbot.dataclasses.AnswerDetails
import com.supergrecko.questionbot.dataclasses.Question
import com.supergrecko.questionbot.extensions.PermissionLevel
import com.supergrecko.questionbot.extensions.permission
import com.supergrecko.questionbot.services.AnswerService
import com.supergrecko.questionbot.services.ConfigService
import com.supergrecko.questionbot.tools.Arguments
import me.aberrantfox.kjdautils.api.dsl.CommandSet
import me.aberrantfox.kjdautils.api.dsl.commands
import me.aberrantfox.kjdautils.internal.arguments.SentenceArg

@CommandSet("answer")
fun answerCommands(config: ConfigService, answerService: AnswerService) = commands {
    command("answer") {
        description = "Answer a question"
        requiresGuild = true
        permission = PermissionLevel.EVERYONE

        expect(QuestionArg, SentenceArg)

        execute {
            val args = Arguments(it.args)
            // These will always exist
            val question = args.asType<Question>(0)
            val answer = args.asType<String>(1)

            val state = config.getGuild(it.guild?.id!!)
            val details = AnswerDetails(it.author, it.message.id, question.id, answer)
            val channel = it.guild!!.getTextChannelById(state.config.channels.answers) ?: it.guild!!.textChannels.first()

            if (answerService.questionAnsweredByUser(it.guild!!, details)) {
                it.respond("You have already answered Question#${question.id}. Check ${channel.asMention}")
            } else {
                answerService.addAnswer(it.guild!!, details)
                it.respond("Your answer has been posted in ${channel.asMention}")
            }
        }
    }
}
