package com.supergrecko.questionbot.dataclasses

import net.dv8tion.jda.api.entities.User


/**
 * Represent answer details that will be used to save and send answer
 *
 * @property sender the user that sent the answer
 * @property questionId the id of the question that is being answered
 * @property text the text content of the answer
 */
data class AnswerDetails(
        var sender: User,
        var questionId: Int = 0,
        var text: String = ""
)
