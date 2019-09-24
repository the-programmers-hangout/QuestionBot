package com.supergrecko.questionbot.dataclasses

import net.dv8tion.jda.api.entities.TextChannel

/**
 * Represent an answer
 *
 * @property authorSnowflake the uid who sent the answer
 * @property messageSnowflake the message id for the reply embed
 */
data class Answer(
        var authorSnowflake: String = "",
        var messageSnowflake: String = ""
) {
    fun setEmbedId(id: String) = run { this.messageSnowflake = id }
    fun deleteMessage(channel: TextChannel) = channel.deleteMessageById(messageSnowflake).queue()
}
