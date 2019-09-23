package com.supergrecko.questionbot.listeners

import com.google.common.eventbus.Subscribe
import com.supergrecko.questionbot.services.ConfigService
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent

class GuildLeaveListener(private val service: ConfigService) {
    @Subscribe
    fun onMessage(event: GuildLeaveEvent) {
        with (event) {
            service.unregister(event.guild)
        }
    }
}
