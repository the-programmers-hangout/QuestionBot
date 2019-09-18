package com.supergrecko.questionbot.services

import com.supergrecko.questionbot.dataclasses.BotConfig
import me.aberrantfox.kjdautils.api.annotation.Service
import me.aberrantfox.kjdautils.api.dsl.CommandEvent
import net.dv8tion.jda.api.entities.TextChannel
import me.aberrantfox.kjdautils.api.dsl.embed
import me.aberrantfox.kjdautils.internal.arguments.OnOffArg
import java.awt.Color
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class LogService(val config: BotConfig) {
    fun log(event: CommandEvent) {
        val logsEnabled = config.guilds.find { it.guild == event.guild?.id }?.loggingEnabled
        if (!logsEnabled!!) return
        retrieveLoggingChannel(event)?.sendMessage(createEmbed(event))?.queue()
    }

    private fun createEmbed(event: CommandEvent) = embed {
        val jumpToLink = "https://discordapp.com/channels/${event.guild?.id}/${event.channel.id}/${event.message.id}"
        color = Color(0xfb8c00)
        thumbnail = event.author.effectiveAvatarUrl
        title = "New Command: ${event.commandStruct.commandName}"
        description = event.message.contentRaw
        addInlineField("Invoked By", event.author.name)
        addInlineField("Date", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE))
        addField("Link:", jumpToLink)
    }

    private fun retrieveLoggingChannel(event: CommandEvent): TextChannel? {
        val id = config.guilds[0].logChannel.takeIf { it != "<missing channel>" }

        if (id == null) {
            // TODO: implement better way of display no log channel
            event.channel.sendMessage("Log Channel has not been configured. To configure, invoke `\$setlog <id>`").queue()
            return null
        }

        return event.guild!!.jda.getTextChannelById(id)
    }
}
