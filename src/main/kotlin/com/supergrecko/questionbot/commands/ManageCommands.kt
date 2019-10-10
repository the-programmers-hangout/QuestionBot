package com.supergrecko.questionbot.commands

import com.supergrecko.questionbot.arguments.QuestionArg
import com.supergrecko.questionbot.dataclasses.AnswerImpl
import com.supergrecko.questionbot.extensions.PermissionLevel
import com.supergrecko.questionbot.extensions.permission
import com.supergrecko.questionbot.services.AnswerService
import com.supergrecko.questionbot.services.ConfigService
import com.supergrecko.questionbot.dataclasses.LogChannels
import com.supergrecko.questionbot.services.QuestionService
import me.aberrantfox.kjdautils.api.dsl.command.CommandSet
import me.aberrantfox.kjdautils.api.dsl.command.commands
import me.aberrantfox.kjdautils.extensions.jda.fullName
import me.aberrantfox.kjdautils.internal.arguments.*

@CommandSet("Configure")
fun manageCommands(config: ConfigService, questions: QuestionService, answerService: AnswerService) = commands {
    command("SetRole") {
        description = "Set the lowest required role to invoke commands."
        permission = PermissionLevel.ADMIN

        execute(RoleArg) {
            val role = it.args.first
            config.setAdminRole(it.guild!!.id, role.name)

            it.respond("Success, the minimum required role to invoke admin commands was set to `${role.name}`.")
        }
    }

    command("SetPrefix") {
        description = "Sets the bot prefix."
        permission = PermissionLevel.ADMIN

        execute(WordArg) {
            val prefix = it.args.first
            questions.setPrefix(prefix, it.guild!!)

            it.respond("Success, the bot prefix has been set to `$prefix`.")
        }
    }

    command("SetChannel") {
        description = "Sets the output channel for the given argument."
        permission = PermissionLevel.ADMIN

        execute(ChoiceArg("type", "log", "questions", "answers"), TextChannelArg) {
            // These will always exist
            val command = it.args.first
            val channel = it.args.second

            when (command) {
                "log" -> config.setChannel(LogChannels.LOG, it.guild?.id!!, channel)
                "questions" -> config.setChannel(LogChannels.QUESTION, it.guild?.id!!, channel)
                "answers" -> config.setChannel(LogChannels.ANSWER, it.guild?.id!!, channel)

                // Should be unreachable
                else -> return@execute it.respond("Error, the option $command is not valid.")
            }

            it.respond("Success, the $command output channel has been set to ${channel.asMention}.")
        }
    }

    command("EnableLogging") {
        description = "Enables / Disables bot logging"
        permission = PermissionLevel.ADMIN

        execute(ChoiceArg("state", "on", "off")) {
            val param = it.args.first
            config.enableLogging(it.guild?.id!!, param == "on")

            it.respond("Success, logging settings have successfully been updated.")
        }
    }

    command("DelAnswer") {
        description = "Delete an answer from a question."
        permission = PermissionLevel.ADMIN

        execute(QuestionArg, UserArg) {
            val (question, user) = it.args
            val details = AnswerImpl(user, question.id)

            if (answerService.questionAnsweredByUser(it.guild!!, details)) {
                answerService.deleteAnswer(it.guild!!, details)
                it.respond("Answer was deleted")
            } else {
                it.respond("${user.fullName()} has not answered Question #${question.id}.")
            }
        }
    }

    command("AddAnswer") {
        description = "Manually add an already existing message as a reply to a question"
        permission = PermissionLevel.ADMIN

        execute(QuestionArg, UserArg, SentenceArg) {
            val (question, user, answer) = it.args

            val state = config.getGuild(it.guild?.id!!)
            val details = AnswerImpl(user, question.id, answer)

            // This is always valid because the precondition checks the channel's existence
            val channel = it.guild!!.getTextChannelById(state.config.channels.answers)!!

            if (answerService.questionAnsweredByUser(it.guild!!, details)) {
                it.respond("User ${user.fullName()} has already submitted an answer for Question ${question.id}.")
            } else {
                answerService.addAnswer(it.guild!!, details)
                it.respond("Answer for ${user.fullName()} posted in ${channel.asMention}")
            }
        }
    }

    command("ConvertAnswer") {
        description = "Converts an existing message to a QuestionBot answer"
        permission = PermissionLevel.ADMIN

        execute(QuestionArg, MessageArg, TextChannelArg) {
            val (question, messageId, channel) = it.args

            channel.retrieveMessageById(messageId.id).queue { message ->
                val details = AnswerImpl(message.author, question.id, message.contentRaw)

                if (answerService.questionAnsweredByUser(it.guild!!, details)) {
                    it.respond("User ${message.author.fullName()} has already submitted an answer for Question ${question.id}.")
                } else {
                    answerService.addAnswer(it.guild!!, details)
                    it.respond("Answer for ${message.author.fullName()} posted.")
                }
            }
        }
    }

    command("ConvertQuestion") {
        description = "Converts an existing message to a QuestionBot question"
        permission = PermissionLevel.ADMIN

        execute(MessageArg, TextChannelArg, SentenceArg.makeOptional("")) {
            val (messageId, channel, note) = it.args
            val state = config.getGuild(it.guild?.id!!)

            channel.retrieveMessageById(messageId.id).queue { message ->
                questions.addQuestion(it.guild!!, message.author.id, message.contentRaw, note)
                questions.sendQuestion(it.guild!!, state.config.count)
                it.respond("Message ${message.id} converted to question and posted.")
            }
        }
    }
}
