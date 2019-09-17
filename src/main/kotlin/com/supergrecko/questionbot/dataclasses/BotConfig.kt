package com.supergrecko.questionbot.dataclasses

import me.aberrantfox.kjdautils.api.annotation.Data

/**
 * Represent the bot configuration
 *
 * @property owner bot owner snowflake
 * @property prefix the bot command prefix
 * @property guilds the guilds the bot views
 */
@Data("config/config.json")
data class BotConfig(
        val owner: String = "<missing id>",
        val prefix: String = "$",
        val guilds: MutableList<GuildConfig> = mutableListOf()
) {
    fun addGuild(guild: GuildConfig) = guilds.add(guild)
    fun get(id: String) = guilds.firstOrNull { it.guild == id }
    fun removeGuild(id: String) = guilds.removeAll { it.guild == id }
}

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
        val count: Int = 0,
        val questions: MutableList<Question> = mutableListOf()
)

/**
 * Represent a question
 *
 * @property sender the uid who sent the question
 * @property channel the channel this was sent to
 * @property id the guild-level question id
 * @property question the question which was asked.
 */
data class Question(
        val sender: String = "<missing id>",
        val channel: String = "<missing channel>",
        val id: Int = 0,
        val responses: MutableList<String> = mutableListOf(),
        val question: String = "<missing question>"
)
