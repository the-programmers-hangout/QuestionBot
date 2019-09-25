package com.supergrecko.questionbot.listeners

import com.google.common.eventbus.Subscribe
import com.supergrecko.questionbot.services.ConfigService
import com.supergrecko.questionbot.services.QuestionService
import net.dv8tion.jda.api.events.ReadyEvent

class ReadyListener(private val config: ConfigService, private val questions: QuestionService) {
    /**
     * Create prefix if not created and register
     * all guilds
     */
    @Subscribe
    fun onReady(event: ReadyEvent) {
        config.registerGuilds()

        event.jda.guilds.forEach {
            questions.setPrefix(config.config.prefix, it)
        }

        config.save()
    }
}
