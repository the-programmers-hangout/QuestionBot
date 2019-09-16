package com.supergrecko.questionbot.dataclasses

/**
 * Represent the bot configuration
 *
 * @property owner bot owner snowflake
 * @property prefix the bot command prefix
 * @property guilds the guilds the bot views
 */
data class BotConfig(
        val owner: String = "<missing id>",
        val prefix: String = "$",
        val guilds: MutableList<GuildConfig> = mutableListOf()
)

/**
 * Represent a guild
 *
 * @property guild the guild snowflake
 * @property role the required role name
 * @property count amount of questions asked in this guild
 */
data class GuildConfig(
        val guild: String = "<missing role>",
        val role: String = "<missing id>",
        val count: Int = 0
)
