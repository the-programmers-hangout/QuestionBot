package com.supergrecko.questionbot.services

import com.google.gson.Gson
import com.supergrecko.questionbot.dataclasses.BotConfig
import me.aberrantfox.kjdautils.api.annotation.Service
import me.aberrantfox.kjdautils.api.dsl.embed
import me.aberrantfox.kjdautils.extensions.jda.fullName
import net.dv8tion.jda.api.entities.Guild
import java.awt.Color

@Service
class InfoService(private val configuration: BotConfig) {
    private data class Properties(val version: String, val author: String, val repository: String)

    private val propFile = Properties::class.java.getResource("/properties.json").readText()

    private val project = Gson().fromJson(propFile, Properties::class.java)

    fun infoEmbed(guild: Guild) = embed {
        val self = guild.jda.selfUser

        // TODO: add more fields
        color = Color(0xfb8c00)
        thumbnail = self.effectiveAvatarUrl

        addField(self.fullName(), "QuestionBot")

        addInlineField("Prefix", configuration.prefix)
        addInlineField("Version", project.version)
        addInlineField("Source", project.repository)
    }
}
