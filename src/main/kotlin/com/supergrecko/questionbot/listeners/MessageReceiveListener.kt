package com.supergrecko.questionbot.listeners

import com.google.common.eventbus.Subscribe
import com.supergrecko.questionbot.services.InfoService
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

class MessageReceiveListener(private val service: InfoService) {
    /**
     * MessageReceive listener to detect if Bot info was
     * called.
     *
     * @param event the event
     */
    @Subscribe
    fun onMessage(event: GuildMessageReceivedEvent) {
        with (event) {
            if (author.isBot) {
                return
            }

            if (message.contentRaw == jda.selfUser.asMention) {
                channel.sendMessage(service.infoEmbed(guild)).queue()
            }
        }
    }
}

