package com.supergrecko.questionbot.dataclasses

/**
 * Represent the output channels
 *
 * @property logs the channel where logs are sent
 * @property questions the channel where questions are sent
 * @property answers the channel where answers are sent
 */
data class GuildChannels(
        var logs: String = "0",
        var questions: String = "0",
        var answers: String = "0",
        var replyTo: String = "0"
)
