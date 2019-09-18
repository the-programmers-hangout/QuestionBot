package com.supergrecko.questionbot.services

import com.supergrecko.questionbot.dataclasses.BotConfig
import me.aberrantfox.kjdautils.api.annotation.Service
import me.aberrantfox.kjdautils.api.dsl.CommandEvent
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.TextChannel
import me.aberrantfox.kjdautils.api.dsl.embed
import java.awt.Color
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class LogService(val config: BotConfig) {
    fun log(event: CommandEvent) = retrieveLoggingChannel(event.guild!!)?.sendMessage(createEmbed(event))?.queue()

    private fun createEmbed(event: CommandEvent) = embed {
        var jumpToLink: String = "https://discordapp.com/channels/${event.guild?.id}/${event.channel.id}/${event.message.id}"
        color = Color(0xfb8c00)
        thumbnail = event.author.effectiveAvatarUrl
        title = "New Command: ${event.commandStruct.commandName}"
        description = event.message.contentRaw
        addInlineField("Invoked By", "${event.author.name}")
        addInlineField("Date", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE))
        addField("Goto:", jumpToLink)
    }

    private fun retrieveLoggingChannel(guild: Guild): TextChannel? {
        val channelId = config.guilds[0].logChannel.takeIf { it.isNotEmpty() } ?: return null
        return guild.jda.getTextChannelById(channelId)
    }
}
