package com.supergrecko.questionbot.services

import me.aberrantfox.kjdautils.api.annotation.Service
import me.aberrantfox.kjdautils.api.dsl.CommandEvent
import net.dv8tion.jda.api.entities.TextChannel
import me.aberrantfox.kjdautils.api.dsl.embed
import java.awt.Color
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class LogService(val config: ConfigService) {

    /**
     * Logs an event
     *
     * @param event the invoked command
     */
    fun log(event: CommandEvent) {
        val state = config.getGuild(event.guild!!.id)

        if (!state.config.loggingEnabled) {
            return
        }

        getChannel(event)?.sendMessage(createEmbed(event))?.queue()
    }

    /**
     * Creates a log record embed
     *
     * @param event the invoked command
     */
    private fun createEmbed(event: CommandEvent) = embed {
        val link = "https://discordapp.com/channels/${event.guild?.id}/${event.channel.id}/${event.message.id}"

        color = Color(0xfb8c00)
        thumbnail = event.author.effectiveAvatarUrl
        title = "Command Invoked: ${event.commandStruct.commandName}"
        description = event.message.contentRaw

        addInlineField("Invoked By", event.author.name)
        addInlineField("Date", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE))
        addField("Link:", link)
    }

    /**
     * Pulls the TextChannel which the bog uses to log
     *
     * @param event the command invocation
     */
    private fun getChannel(event: CommandEvent): TextChannel? {
        val state = config.getGuild(event.guild!!.id)

        if (state.config.logChannel == "<missing channel>") {
            // TODO: implement better way of display no log channel
            event.channel.sendMessage("Log Channel has not been configured. To configure, invoke `\$setlogchannel <channel>`").queue()
            return null
        }

        return event.guild!!.jda.getTextChannelById(state.config.logChannel)
    }
}
