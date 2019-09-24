package com.supergrecko.questionbot.dataclasses

/**
 * Represent a question
 *
 * @property authorSnowflake the uid who sent the question
 * @property channelSnowflake the channel this was sent to
 * @property id the guild-level question id
 * @property responses the answers to this question
 * @property question the question which was asked.
 * @property note the question note, if any
 */
data class Question(
        var authorSnowflake: String = "",
        var channelSnowflake: String = "",
        var messageSnowflake: String = "",
        val responses: MutableList<Answer> = mutableListOf(),
        var id: Int = 0,
        var question: String = "",
        var note: String = ""
) {
    fun edit(question: String, note: String) = run {
        this.note = note
        this.question = question
    }

    fun add(answer: Answer) = responses.add(answer)
    fun get(author: String) = responses.find{ it.authorSnowflake == author }
    fun deleteBy(author: String) = responses.removeAll{ it.authorSnowflake == author }
}
