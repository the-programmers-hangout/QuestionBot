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
    fun get(id: String) = guilds.firstOrNull { it.guild == id }
    fun removeGuild(id: String) = guilds.removeAll { it.guild == id }
}

/**
 * Represent a guild
 *
 * @property guild the guild snowflake
 * @property role the required role name
 * @property count amount of questions asked in this guild
 * @property logChannel the channel to emit logs to
 * @property questionChannel the channel to emit questions to
 * @property loggingEnabled whether logging is enabled or not
 */
data class GuildConfig(
        var guild: String = "<missing role>",
        var role: String = "<missing id>",
        var count: Int = 0,
        var logChannel: String = "<missing channel>",
        var questionChannel: String = "<missing channel>",
        var loggingEnabled: Boolean = true,
        val questions: MutableList<Question> = mutableListOf()
) {
    fun addQuestion(question: Question) = questions.add(question)
}

/**
 * Represent a question
 *
 * @property sender the uid who sent the question
 * @property channel the channel this was sent to
 * @property id the guild-level question id
 * @property responses the answers to this question
 * @property question the question which was asked.
 * @property note the question note, if any
 */
data class Question(
        var sender: String = "<missing id>",
        var channel: String = "<missing channel>",
        var id: Int = 0,
        val responses: MutableList<Answer> = mutableListOf(),
        var question: String = "<missing question>",
        var note: String = "",
        var message: String = "<missing id>"
) {
    fun update(question: String, note: String) {
        this.note = note
        this.question = question
    }
}

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
