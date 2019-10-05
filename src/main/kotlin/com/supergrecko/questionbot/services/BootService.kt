package com.supergrecko.questionbot.services

import me.aberrantfox.kjdautils.api.annotation.Service
import me.aberrantfox.kjdautils.discord.Discord

@Service
class BootService(private val config: ConfigService, discord: Discord, questions: QuestionService) {
    init {
        config.registerGuilds()

        discord.jda.guilds.forEach {
            // Update existing question embeds
            questions.setPrefix(config.config.prefix, it)
        }

        config.save()
    }
}
