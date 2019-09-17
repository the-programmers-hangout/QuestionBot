package com.supergrecko.questionbot.services

import com.supergrecko.questionbot.dataclasses.BotConfig
import com.supergrecko.questionbot.dataclasses.GuildConfig
import me.aberrantfox.kjdautils.api.annotation.Service
import me.aberrantfox.kjdautils.discord.Discord
import me.aberrantfox.kjdautils.internal.di.PersistenceService

private lateinit var guilds: MutableList<GuildConfig>

/**
 * Get a guild by its id
 *
 * @param id the guild snowflake
 */
fun getGuild(id: String?): GuildConfig {
    return guilds.first { it.guild == id }
}

@Service
class QuestionService(val config: BotConfig, private val discord: Discord, private val store: PersistenceService) {
    init {
        // Hacky way to extract guilds from service
        guilds = config.guilds
    }
}
