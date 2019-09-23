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
    /**
     * Gets a guild from a snowflake if it's registered in the config
     *
     * @param id snowflake to search for
     */
    fun get(id: String) = guilds.firstOrNull { it.guildSnowflake == id }

    /**
     * Removes a guild from the config
     *
     * @param id the guild snowflake to wipe
     */
    fun removeGuild(id: String) = guilds.removeAll { it.guildSnowflake == id }
}
