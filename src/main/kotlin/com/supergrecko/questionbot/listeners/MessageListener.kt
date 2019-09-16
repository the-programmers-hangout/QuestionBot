package com.supergrecko.questionbot.listeners

import com.google.common.eventbus.Subscribe
import com.supergrecko.questionbot.services.InfoService
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

class MessageListener(private val service: InfoService) {
    @Subscribe
    fun onMessage(event: GuildMessageReceivedEvent) {
        with (event) {
            if (author.isBot) {
                return
            }

            if (message.contentRaw == jda.selfUser.asMention) {
                // TODO: Create embed
                channel.sendMessage(service.infoEmbed(guild)).queue()
            }
        }
    }
}

