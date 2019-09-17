package com.supergrecko.questionbot.listeners

import com.google.common.eventbus.Subscribe
import com.supergrecko.questionbot.dataclasses.BotConfig
import com.supergrecko.questionbot.services.InfoService
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent

class GuildLeaveListener(private val service: InfoService) {
    @Subscribe
    fun onMessage(event: GuildLeaveEvent) {
        with (event) {

        }
    }
}
