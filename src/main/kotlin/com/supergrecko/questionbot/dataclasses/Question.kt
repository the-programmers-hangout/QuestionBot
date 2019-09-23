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
    /**
     * Edit a question's question and note
     *
     * @param question the question to ask
     * @param note the question note, if any
     */
    fun edit(question: String, note: String) = run {
        this.note = note
        this.question = question
    }

    /**
     * Adds an answer
     *
     * @param answer the answer to add
     */
    fun add(answer: Answer) = responses.add(answer)

    /**
     * Gets an answer. This is safe because each user can only reply once to each question.
     *
     * @param author the snowflake of the author.
     */
    fun get(author: String) = responses.find{ it.authorSnowflake == author }

    /**
     * Deletes an answer from the given author. This is safe because each user can only reply once to each question.
     *
     * @param author the snowflake of the author to delete
     */
    fun deleteBy(author: String) = responses.removeAll{ it.authorSnowflake == author }
}
