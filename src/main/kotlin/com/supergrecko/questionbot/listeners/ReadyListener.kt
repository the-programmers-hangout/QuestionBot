package com.supergrecko.questionbot.listeners

import com.google.common.eventbus.Subscribe
import com.supergrecko.questionbot.services.ConfigService
import net.dv8tion.jda.api.events.ReadyEvent

class ReadyListener(private val config: ConfigService) {
    @Subscribe
    fun onReady(event: ReadyEvent) {
        config.setPrefix()
        config.reloadServers()

        config.save()
    }
}
