package com.supergrecko.questionbot.dataclasses

import net.dv8tion.jda.api.entities.TextChannel

/**
 * Represent an answer
 *
 * @property authorSnowflake the uid who sent the answer
 * @property invocationSnowflake the message id for the command invocation
 * @property messageSnowflake the message id for the reply embed
 */
data class Answer(
        var authorSnowflake: String = "",
        var messageSnowflake: String = "",
        var invocationSnowflake: String = ""
) {
    fun setEmbedId(embedId: String) = run { this.messageSnowflake = embedId }
    fun setInvocationId(invocationId: String) = run { this.invocationSnowflake = invocationId }
    fun deleteMessage(channel: TextChannel) = channel.deleteMessageById(messageSnowflake).queue()
    fun update(embed: String, invocation: String) = run {
        this.messageSnowflake = embed
        this.invocationSnowflake = invocation
    }
}
