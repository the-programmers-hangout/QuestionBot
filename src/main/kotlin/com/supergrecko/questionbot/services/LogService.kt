package com.supergrecko.questionbot.services

import me.aberrantfox.kjdautils.api.annotation.Service
import me.aberrantfox.kjdautils.api.dsl.CommandEvent
import net.dv8tion.jda.api.entities.TextChannel
import me.aberrantfox.kjdautils.api.dsl.embed
import java.awt.Color

@Service
class LogService(val config: ConfigService) {

    fun log(event: CommandEvent) {
        val state = config.getGuild(event.guild!!.id)

        if (!state.config.loggingEnabled) {
            return
        }

        getChannel(event)?.sendMessage(createEmbed(event))?.queue()
    }

    private fun createEmbed(event: CommandEvent) = embed {
        val link = "https://discordapp.com/channels/${event.guild?.id}/${event.channel.id}/${event.message.id}"

        color = Color(0xfb8c00)
        title = "Command Invoked"

        addInlineField("Command", event.commandStruct.commandName)
        addInlineField("Author ID", event.author.id)
        addInlineField("Original Message", "[Link]($link)")
        addField("Command Text", event.message.contentDisplay)

        author {
            name = event.author.asTag
            iconUrl = event.author.effectiveAvatarUrl
        }

        footer {
            timeStamp = event.message.timeCreated
        }
    }

    private fun getChannel(event: CommandEvent): TextChannel? {
        val state = config.getGuild(event.guild!!.id)

        if (state.config.channels.logs == "") {
            // TODO: implement better way of display no log channel
            event.channel.sendMessage("Log Channel has not been configured. To configure, invoke `\$setlogchannel <channel>`").queue()
            return null
        }

        return event.guild!!.jda.getTextChannelById(state.config.channels.logs)
    }
}
