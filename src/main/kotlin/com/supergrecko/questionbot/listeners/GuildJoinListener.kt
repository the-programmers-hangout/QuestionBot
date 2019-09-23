package com.supergrecko.questionbot.listeners

import com.google.common.eventbus.Subscribe
import com.supergrecko.questionbot.services.ConfigService
import net.dv8tion.jda.api.events.guild.GuildJoinEvent

class GuildJoinListener(private val service: ConfigService) {
    @Subscribe
    fun onMessage(event: GuildJoinEvent) {
        with (event) {
            service.registerGuilds()
        }
    }
}

