package com.supergrecko.questionbot.dataclasses

import me.aberrantfox.kjdautils.api.annotation.Data

/**
 * Represent the bot configuration
 *
 * @property prefix the bot command prefix
 * @property guilds the guilds the bot views
 */
@Data("config/config.json")
data class BotConfig(
        var prefix: String = "$",
        val guilds: MutableList<GuildConfig> = mutableListOf()
) {
    fun get(id: String) = guilds.firstOrNull { it.guildSnowflake == id }
    fun removeGuild(id: String) = guilds.removeAll { it.guildSnowflake == id }
}
