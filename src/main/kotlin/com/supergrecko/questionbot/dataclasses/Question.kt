package com.supergrecko.questionbot.dataclasses

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
    fun getAnswerByAuthor(authorId: String) = responses.find{ it -> it.sender == authorId }
    fun deleteAnswerByAuthor(authorId: String) = responses.removeAll{ it.sender == authorId }
}
