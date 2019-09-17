package com.supergrecko.questionbot.listeners

import com.google.common.eventbus.Subscribe
import com.supergrecko.questionbot.services.ConfigService
import net.dv8tion.jda.api.events.ReadyEvent

class ReadyListener(private val config: ConfigService) {
    /**
     * Create prefix if not created and register
     * all guilds
     */
    @Subscribe
    fun onReady(event: ReadyEvent) {
        config.setPrefix()
        config.registerGuilds()

        config.save()
    }
}
