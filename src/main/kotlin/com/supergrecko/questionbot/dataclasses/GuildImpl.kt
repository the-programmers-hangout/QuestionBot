package com.supergrecko.questionbot.dataclasses

import net.dv8tion.jda.api.entities.Guild

data class GuildImpl(
        val config: GuildConfig,
        val guild: Guild
) {
    fun getQuestion(id: Int) = config.questions.first { it.id == id }
}
