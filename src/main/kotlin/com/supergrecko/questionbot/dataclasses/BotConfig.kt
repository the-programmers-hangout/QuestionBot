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
        val responses: MutableList<Answer> = mutableListOf(),
        val question: String = "<missing question>"
)

/**
 * Represent an answer
 *
 * @property sender the uid who sent the answer
 * @property listed whether the answer has been deleted or not
 * @property reason if it was deleted: why?
 * @property answer the answer message id
 */
data class Answer(
        val sender: String = "<missing id>",
        val listed: Boolean = true,
        val reason: String = "This answer is still listed.",
        val answer: String = "<missing message>"
)
