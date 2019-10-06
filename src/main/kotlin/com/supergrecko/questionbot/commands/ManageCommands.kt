package com.supergrecko.questionbot.commands

import com.supergrecko.questionbot.arguments.QuestionArg
import com.supergrecko.questionbot.dataclasses.AnswerImpl
import com.supergrecko.questionbot.dataclasses.Question
import com.supergrecko.questionbot.extensions.PermissionLevel
import com.supergrecko.questionbot.extensions.permission
import com.supergrecko.questionbot.services.AnswerService
import com.supergrecko.questionbot.services.ConfigService
import com.supergrecko.questionbot.dataclasses.LogChannels
import com.supergrecko.questionbot.services.QuestionService
import com.supergrecko.questionbot.tools.Arguments
import me.aberrantfox.kjdautils.api.dsl.CommandSet
import me.aberrantfox.kjdautils.api.dsl.arg
import me.aberrantfox.kjdautils.api.dsl.commands
import me.aberrantfox.kjdautils.extensions.jda.fullName
import me.aberrantfox.kjdautils.internal.arguments.*
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.internal.entities.RoleImpl
import net.dv8tion.jda.internal.entities.TextChannelImpl

@CommandSet("Configure")
fun manageCommands(config: ConfigService, questions: QuestionService, answerService: AnswerService) = commands {
    command("SetRole") {
        description = "Set the lowest required role to invoke commands."
        permission = PermissionLevel.ADMIN

        expect(RoleArg)

        execute {
            // These will always exist
            val args = Arguments(it.args)
            val role = args.asType<RoleImpl>(0)
            config.setAdminRole(it.guild!!.id, role.name)

            it.respond("Success, the minimum required role to invoke admin commands was set to `${role.name}`.")
        }
    }

    command("SetPrefix") {
        description = "Sets the bot prefix."
        permission = PermissionLevel.ADMIN

        expect(WordArg)

        execute {
            val prefix = it.args.first() as String
            questions.setPrefix(prefix, it.guild!!)

            it.respond("Success, the bot prefix has been set to `$prefix`.")
        }
    }

    command("SetChannel") {
        description = "Sets the output channel for the given argument."
        permission = PermissionLevel.ADMIN

        expect(ChoiceArg("ChoiceArg", "log", "questions", "answers", "replyto"), TextChannelArg)

        execute {
            val args = Arguments(it.args)
            // These will always exist
            val command = args.asType<String>(0)
            val channel = args.asType<TextChannelImpl>(1)

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

        expect(ChoiceArg("ChoiceArg", "on", "off"))

        execute {
            // These will always exist
            val args = Arguments(it.args)
            val param = args.asType<String>(0)

            config.enableLogging(it.guild?.id!!, param == "on")

            it.respond("Success, logging settings have successfully been updated.")
        }
    }

    command("DelAnswer") {
        description = "Delete an answer from a question."
        permission = PermissionLevel.ADMIN

        expect(QuestionArg, UserArg)

        execute {
            val args = Arguments(it.args)
            // These will always exist
            val question = args.asType<Question>(0)
            val user = args.asType<User>(1)
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

        expect(QuestionArg, UserArg, SentenceArg)

        execute {
            val args = Arguments(it.args)
            // These will always exist
            val question = args.asType<Question>(0)
            val user = args.asType<User>(1)
            val answer = args.asType<String>(2)
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

        expect(QuestionArg, MessageArg, TextChannelArg)

        execute {
            val args = Arguments(it.args)
            val question = args.asType<Question>(0)
            val messageId = args.asType<Message>(1)
            val channel = args.asType<TextChannel>(2)

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

        expect(arg(MessageArg), arg(TextChannelArg), arg(SentenceArg, optional = true, default = ""))

        execute {
            val args = Arguments(it.args)
            val messageId = args.asType<Message>(0)
            val channel = args.asType<TextChannel>(1)
            val note = args.asType<String>(2)
            val state = config.getGuild(it.guild?.id!!)

            channel.retrieveMessageById(messageId.id).queue { message ->
                questions.addQuestion(it.guild!!, message.author.id, message.contentRaw, note)
                questions.sendQuestion(it.guild!!, state.config.count)
                it.respond("Message ${message.id} converted to question and posted.")
            }
        }
    }
}
