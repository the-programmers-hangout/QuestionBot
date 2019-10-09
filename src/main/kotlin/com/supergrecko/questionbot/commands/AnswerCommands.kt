package com.supergrecko.questionbot.commands

import com.supergrecko.questionbot.arguments.QuestionArg
import com.supergrecko.questionbot.dataclasses.AnswerImpl
import com.supergrecko.questionbot.dataclasses.Question
import com.supergrecko.questionbot.extensions.PermissionLevel
import com.supergrecko.questionbot.extensions.permission
import com.supergrecko.questionbot.services.AnswerService
import com.supergrecko.questionbot.services.ConfigService
import com.supergrecko.questionbot.tools.Arguments
import me.aberrantfox.kjdautils.api.dsl.command.CommandSet
import me.aberrantfox.kjdautils.api.dsl.command.commands
import me.aberrantfox.kjdautils.api.dsl.respond
import me.aberrantfox.kjdautils.internal.arguments.SentenceArg

@CommandSet("Answer")
fun answerCommands(config: ConfigService, answerService: AnswerService) = commands {
    command("Answer") {
        description = "Answer a question"
        permission = PermissionLevel.EVERYONE

        execute(QuestionArg, SentenceArg) {
            val question = it.args.first
            val answer = it.args.second

            val state = config.getGuild(it.guild?.id!!)
            val details = AnswerImpl(it.author, question.id, answer)

            // This is always valid because the precondition checks the channel's existence
            val channel = it.guild!!.getTextChannelById(state.config.channels.answers)!!

            if (answerService.questionAnsweredByUser(it.guild!!, details)) {
                it.respond("You have already answered Question#${question.id}. Check ${channel.asMention}")
            } else {
                answerService.addAnswer(it.guild!!, details)
                it.respond("Your answer has been posted in ${channel.asMention}")
            }
        }
    }

    command("Edit") {
        description = "Edit an answer to a question"
        permission = PermissionLevel.EVERYONE

        execute(QuestionArg, SentenceArg) {
            val question = it.args.first
            val answer = it.args.second
            val details = AnswerImpl(it.author, question.id, answer)

            if (answerService.questionAnsweredByUser(it.guild!!, details)) {
                answerService.editAnswer(it.guild!!, details)
                it.respond("Your answer was updated.")
            } else {
                it.respond("You have not answered Question#${question.id}.")
            }
        }
    }

    command("Delete") {
        description = "Delete an answer to a question"
        permission = PermissionLevel.EVERYONE

        execute(QuestionArg) {
            val question = it.args.first
            val details = AnswerImpl(it.author, question.id)

            if (answerService.questionAnsweredByUser(it.guild!!, details)) {
                answerService.deleteAnswer(it.guild!!, details)
                it.respond("Your answer was deleted.")
            } else {
                it.respond("You have not answered Question#${question.id}.")
            }
        }
    }

    command("List") {
        description = "List answers to a given question"
        permission = PermissionLevel.EVERYONE

        execute(QuestionArg) {
            val question = it.args.first
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
