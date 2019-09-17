package com.supergrecko.questionbot.dataclasses

import me.aberrantfox.kjdautils.api.annotation.Data

/**
 * Represent the bot configuration
 *
 * @property owner bot owner snowflake
 * @property guilds the guilds the bot views
 */
@Data("config/config.json")
data class BotConfig(
        var prefix: String = "$",
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
        var guild: String = "<missing role>",
        var role: String = "<missing id>",
        var count: Int = 0,
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
        var sender: String = "<missing id>",
        var channel: String = "<missing channel>",
        var id: Int = 0,
        val responses: MutableList<Answer> = mutableListOf(),
        var question: String = "<missing question>"
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
        var sender: String = "<missing id>",
        var listed: Boolean = true,
        var reason: String = "This answer is still listed.",
        var answer: String = "<missing message>"
)
