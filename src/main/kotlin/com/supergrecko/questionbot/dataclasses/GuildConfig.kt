package com.supergrecko.questionbot.dataclasses

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
