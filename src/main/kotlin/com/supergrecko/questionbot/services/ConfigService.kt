package com.supergrecko.questionbot.services

import com.supergrecko.questionbot.dataclasses.BotConfig
import me.aberrantfox.kjdautils.api.annotation.Service
import me.aberrantfox.kjdautils.discord.Discord
import me.aberrantfox.kjdautils.internal.di.PersistenceService

@Service
open class ConfigService(val config: BotConfig, private val discord: Discord, private val store: PersistenceService) {
    fun setPrefix(prefix: String = config.prefix) {
        config.prefix = prefix
        discord.configuration.prefix = prefix
    }

    fun save() = store.save(config)

    fun reloadServers() {
        val unsaved = discord.jda.guilds.forEach { guild ->
            config.guilds.filter { it.guild == guild.id }.isEmpty()
        }
    }
}
