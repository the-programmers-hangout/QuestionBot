package com.supergrecko.questionbot.commands

import com.supergrecko.questionbot.arguments.QuestionArg
import com.supergrecko.questionbot.dataclasses.Question
import com.supergrecko.questionbot.extensions.PermissionLevel
import com.supergrecko.questionbot.extensions.permission
import com.supergrecko.questionbot.services.ConfigService
import me.aberrantfox.kjdautils.api.dsl.CommandSet
import me.aberrantfox.kjdautils.api.dsl.commands
import me.aberrantfox.kjdautils.api.dsl.embed
import me.aberrantfox.kjdautils.internal.arguments.SentenceArg
import me.aberrantfox.kjdautils.internal.arguments.SplitterArg
import me.aberrantfox.kjdautils.internal.arguments.TextChannelArg
import net.dv8tion.jda.api.entities.TextChannel
import java.awt.Color

@CommandSet("questions")
fun questionCommands(config: ConfigService) = commands {
    command("ask") {
        description = "Ask the channel a question."
        requiresGuild = true
        permission = PermissionLevel.ADMIN

        expect(TextChannelArg, SplitterArg)

        execute {
            val channel = it.args[0] as TextChannel
            val question = (it.args[1] as List<String?>).getOrNull(0)
            val note = (it.args[1] as List<String?>).getOrNull(1)

            val guild = config.config.guilds.first { c -> c.guild == it.guild?.id }
            guild.questions.add(Question(
                    sender = it.author.id,
                    channel = channel.id,
                    id = guild.count + 1,
                    question = question ?: "No Question was asked",
                    note = note ?: ""
            ))

            val embed = embed {
                color = Color(0xfb8c00)
                thumbnail = it.author.effectiveAvatarUrl
                title = "${it.author.name} has asked a question! (#${guild.count + 1})"
                description = question

                if (note != null) {
                    addBlankField(false)
                    addField("Notes:", note)
                }
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

        }
    }

    command("delete") {
        description = "Delete a question"
        requiresGuild = true
        permission = PermissionLevel.ADMIN

        expect(QuestionArg)

        execute {

        }
    }
}
