package com.supergrecko.questionbot.commands

import com.supergrecko.questionbot.arguments.QuestionArg
import com.supergrecko.questionbot.extensions.PermissionLevel
import com.supergrecko.questionbot.extensions.permission
import com.supergrecko.questionbot.services.ConfigService
import com.supergrecko.questionbot.services.LogChannels
import com.supergrecko.questionbot.services.QuestionService
import com.supergrecko.questionbot.tools.Arguments
import me.aberrantfox.kjdautils.api.dsl.CommandSet
import me.aberrantfox.kjdautils.api.dsl.commands
import me.aberrantfox.kjdautils.internal.arguments.*
import net.dv8tion.jda.internal.entities.RoleImpl
import net.dv8tion.jda.internal.entities.TextChannelImpl

@CommandSet("Configure")
fun manageCommands(config: ConfigService, questions: QuestionService) = commands {
    command("setrole") {
        description = "Set the lowest required role to invoke commands."
        requiresGuild = true
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

    command("setprefix") {
        description = "Sets the bot prefix."
        requiresGuild = true
        permission = PermissionLevel.ADMIN

        expect(WordArg)

        execute {
            val prefix = it.args.first() as String
            questions.setPrefix(prefix, it.guild!!)

            it.respond("Success, the bot prefix has been set to `$prefix`.")
        }
    }

    command("setchannel") {
        description = "Sets the output channel for the given argument."
        requiresGuild = true
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

    command("enablelogging") {
        description = "Enables / Disables bot logging"
        requiresGuild = true
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

    command("delanswer") {
        description = "Delete an answer from a question."
        requiresGuild = true
        permission = PermissionLevel.ADMIN

        expect(MessageArg)

        execute {
        }
    }

    command("addanswer") {
        description = "Manually add an already existing message as a reply to a question"
        requiresGuild = true
        permission = PermissionLevel.ADMIN

        expect(MessageArg, QuestionArg)

        execute {
        }
    }
}
