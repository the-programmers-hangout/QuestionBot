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
    /**
     * Sets the message snowflake where the RichEmbed was posted
     *
     * @param id the snowflake to set
     */
    fun setEmbedId(id: String) = run { this.messageSnowflake = id }

    @Deprecated("Delete this")
    fun setInvocationId(invocationId: String) = run { this.invocationSnowflake = invocationId }

    /**
     * Deletes the embed message
     *
     * @param channel the textchannel to remove from
     */
    fun deleteMessage(channel: TextChannel) = channel.deleteMessageById(messageSnowflake).queue()
}
