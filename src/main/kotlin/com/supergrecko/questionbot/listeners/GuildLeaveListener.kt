package com.supergrecko.questionbot.listeners

import com.google.common.eventbus.Subscribe
import com.supergrecko.questionbot.services.ConfigService
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent

/**
 * Unregisters the guild when the bot is removed from a guild
 */
class GuildLeaveListener(private val service: ConfigService) {
    @Subscribe
    fun onMessage(event: GuildLeaveEvent) {
        with (event) {
            service.unregister(guild)
        }
    }
}
