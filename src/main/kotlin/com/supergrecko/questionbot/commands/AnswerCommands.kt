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
import me.aberrantfox.kjdautils.api.dsl.respond
import me.aberrantfox.kjdautils.internal.arguments.SentenceArg

@CommandSet("Answer")
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
            val details = AnswerDetails(it.author, question.id, answer)

            val channel = it.guild!!.getTextChannelById(state.config.channels.answers)
                    ?: it.guild!!.textChannels.first()

            if (answerService.questionAnsweredByUser(it.guild!!, details)) {
                it.respond("You have already answered Question#${question.id}. Check ${channel.asMention}")
            } else {
                answerService.addAnswer(it.guild!!, details)
                it.respond("Your answer has been posted in ${channel.asMention}")
            }
        }
    }

    command("edit") {
        description = "Edit an answer to a question"
        requiresGuild = true
        permission = PermissionLevel.EVERYONE

        expect(QuestionArg, SentenceArg)

        execute {
            val args = Arguments(it.args)
            val question = args.asType<Question>(0)
            val answer = args.asType<String>(1)
            val details = AnswerDetails(it.author, question.id, answer)

            if (answerService.questionAnsweredByUser(it.guild!!, details)) {
                answerService.editAnswer(it.guild!!, details)
                it.respond("Your answer was updated.")
            } else {
                it.respond("You have not answered Question#${question.id}.")
            }
        }
    }

    command("delete") {
        description = "Delete an answer to a question"
        requiresGuild = true
        permission = PermissionLevel.EVERYONE

        expect(QuestionArg)

        execute {
            val args = Arguments(it.args)
            val question = args.asType<Question>(0)
            val details = AnswerDetails(it.author, question.id)

            if (answerService.questionAnsweredByUser(it.guild!!, details)) {
                answerService.deleteAnswer(it.guild!!, details)
                it.respond("Your answer was deleted.")
            } else {
                it.respond("You have not answered Question#${question.id}.")
            }
        }
    }

    command("list") {
        description = "List answers to a given question"
        requiresGuild = true
        permission = PermissionLevel.EVERYONE

        expect(QuestionArg)

        execute {
            val args = Arguments(it.args)
            val question = args.asType<Question>(0)
            val state = config.getGuild(it.guild!!.id)

            if (state.getQuestion(question.id).responses.size == 0) {
                it.respond("There are no answers for Question #${question.id} yet.")
            } else {
                val msg = answerService.listAnswers(it.guild!!, question.id)
                it.respond(msg)
            }
        }
    }
}
