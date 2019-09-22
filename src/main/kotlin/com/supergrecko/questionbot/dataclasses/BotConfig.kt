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
 * @property channels the channels to output to
 * @property loggingEnabled whether logging is enabled or not
 * @property questions the questions asked in this guild
 */
data class GuildConfig(
        var guild: String = "",
        var role: String = "",
        var count: Int = 0,
        val channels: GuildChannels = GuildChannels(),
        var loggingEnabled: Boolean = true,
        val questions: MutableList<Question> = mutableListOf()
) {
    fun addQuestion(question: Question) = questions.add(question)
    fun deleteQuestion(question: Question, orElse: () -> Unit = {}) {
        val question = questions.find { it.id == question.id }

        if (question != null) {
            questions.remove(question)
            return
        }

        orElse()
    }
}

/**
 * Represent the output channels
 *
 * @property logs the channel where logs are sent
 * @property questions the channel where questions are sent
 * @property answers the channel where answers are sent
 */
data class GuildChannels(
        var logs: String = "0",
        var questions: String = "0",
        var answers: String = "0",
        var replyTo: String = "0"
)

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
        var sender: String = "",
        var channel: String = "",
        var id: Int = 0,
        val responses: MutableList<Answer> = mutableListOf(),
        var question: String = "",
        var note: String = "",
        var message: String = ""
) {
    fun update(question: String, note: String) {
        this.note = note
        this.question = question
    }

    fun addAnswer(answer: Answer) = responses.add(answer)
}

/**
 * Represent an answer
 *
 * @property sender the uid who sent the answer
 * @property listed whether the answer has been deleted or not
 * @property reason if it was deleted: why?
 * @property invocation the message id for the command invocation
 * @property embed the message id for the reply embed
 */
data class Answer(
        var sender: String = "",
        var listed: Boolean = true,
        var reason: String = "This answer is still listed.",
        var invocation: String = "",
        var embed: String = ""
) {
    fun setEmbedId(embedId: String) {
        this.embed = embedId
    }

    fun setInvocationId(invocationId: String) {
        this.invocation = invocationId
    }
}
