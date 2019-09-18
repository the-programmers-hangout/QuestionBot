package com.supergrecko.questionbot.commands

import com.supergrecko.questionbot.arguments.QuestionArg
import com.supergrecko.questionbot.dataclasses.Question
import com.supergrecko.questionbot.extensions.PermissionLevel
import com.supergrecko.questionbot.extensions.permission
import com.supergrecko.questionbot.services.ConfigService
import com.supergrecko.questionbot.services.LogService
import me.aberrantfox.kjdautils.api.dsl.CommandSet
import me.aberrantfox.kjdautils.api.dsl.commands
import me.aberrantfox.kjdautils.api.dsl.embed
import me.aberrantfox.kjdautils.internal.arguments.SentenceArg
import me.aberrantfox.kjdautils.internal.arguments.SplitterArg
import me.aberrantfox.kjdautils.internal.arguments.TextChannelArg
import net.dv8tion.jda.api.entities.TextChannel
import java.awt.Color

@CommandSet("core")
fun questionCommands(config: ConfigService, logService: LogService) = commands {
    command("ask") {
        description = "Ask the channel a question."
        requiresGuild = true
        permission = PermissionLevel.ADMIN

        expect(TextChannelArg, SplitterArg)

        execute {
            logService.log(it)

            val channel = it.args.first() as TextChannel
            val (question, note) = it.args[1] as List<*>
            val guild = config.config.guilds.first { c -> c.guild == it.guild?.id }

            guild.questions.add(Question(
                    sender = it.author.id,
                    channel = channel.id,
                    id = guild.count + 1,
                    question = question as? String ?: "No Question was asked",
                    note = note as? String ?: ""
            ))

            val embed = embed {
                color = Color(0xfb8c00)
                thumbnail = it.author.effectiveAvatarUrl
                title = "${it.author.name} has asked a question! (#${guild.count + 1})"
                description = question as? String

                if (note != null) {
                    addBlankField(false)
                    addField("Notes:", note as? String)
                }

                addBlankField(false)
                // TODO: link the text channel and the reply cmd syntax
                addField("How to reply?", "todo")
            }

            guild.count++
            config.save()
            channel.sendMessage(embed).queue()
            it.respond("Your question has been asked.")
        }
    }

    command("edit") {
        description = "Edit a question"
        requiresGuild = true
        permission = PermissionLevel.ADMIN

        expect(QuestionArg, SentenceArg)

        execute {
            logService.log(it)
            it.respond("test")
        }
    }

    command("delete") {
        description = "Delete a question"
        requiresGuild = true
        permission = PermissionLevel.ADMIN

        expect(QuestionArg)

        execute {
            logService.log(it)
        }
    }
}
