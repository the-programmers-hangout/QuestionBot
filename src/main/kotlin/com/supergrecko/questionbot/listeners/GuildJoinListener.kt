package com.supergrecko.questionbot.listeners

import com.google.common.eventbus.Subscribe
import com.supergrecko.questionbot.services.ConfigService
import net.dv8tion.jda.api.events.guild.GuildJoinEvent

/**
 * Registers the unregistered guilds when the bot joins a new guild.
 */
class GuildJoinListener(private val service: ConfigService) {
    @Subscribe
    fun onMessage(event: GuildJoinEvent) {
        service.registerGuilds()
    }
}

