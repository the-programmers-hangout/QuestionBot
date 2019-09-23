package com.supergrecko.questionbot.dataclasses

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
    fun update(embed: String, invocation: String) {
        this.embed = embed
        this.invocation = invocation
    }
}
