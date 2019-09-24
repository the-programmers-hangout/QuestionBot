package com.supergrecko.questionbot.dataclasses

/**
 * Represent a guild
 *
 * @property guildSnowflake the guild snowflake
 * @property minRoleName the required role name
 * @property count amount of questions asked in this guild
 * @property channels the channels to output to
 * @property loggingEnabled whether logging is enabled or not
 * @property questions the questions asked in this guild
 */
data class GuildConfig(
        var guildSnowflake: String = "",
        var minRoleName: String = "",
        var count: Int = 0,
        var loggingEnabled: Boolean = true,
        val channels: GuildChannels = GuildChannels(),
        val questions: MutableList<Question> = mutableListOf()
) {
    fun addQuestion(question: Question) = questions.add(question)

    /**
     * Deletes a question from the guild
     *
     * @param question the question to delete
     * @param orElse lambda to run if question didn't exist
     */
    fun deleteQuestion(question: Question, orElse: () -> Unit = {}) {
        val q = questions.find { it.id == question.id }

        if (q != null) {
            questions.remove(q)
            return
        }

        orElse()
    }
}
