package com.supergrecko.questionbot

import com.google.gson.Gson
import me.aberrantfox.kjdautils.api.dsl.PrefixDeleteMode
import me.aberrantfox.kjdautils.api.dsl.embed
import me.aberrantfox.kjdautils.api.startBot
import me.aberrantfox.kjdautils.extensions.jda.fullName
import java.awt.Color
import java.lang.IllegalArgumentException

/* Stuff for the mention embed */
private data class Properties(val version: String, val author: String, val repository: String)
private val propFile = Properties::class.java.getResource("/properties.json").readText()
private val project = Gson().fromJson(propFile, Properties::class.java)

fun main(args: Array<String>) {
    val token = args.firstOrNull() ?: throw IllegalArgumentException("No bot token provided.")

    startBot(token) {
        configure {
            allowPrivateMessages = false

            prefix = "$"
            deleteMode = PrefixDeleteMode.Double
            documentationSortOrder = listOf("Ask", "Answer", "Configure", "Utility")

            reactToCommands = false

            mentionEmbed = embed {
                val self = discord.jda.selfUser

                color = Color(0xfb8c00)
                thumbnail = self.effectiveAvatarUrl

                addField(self.fullName(), "QuestionBot")

                addInlineField("Prefix", config.prefix)
                addInlineField("Version", project.version)
                addInlineField("Source", project.repository)
            }
        }
    }
}
