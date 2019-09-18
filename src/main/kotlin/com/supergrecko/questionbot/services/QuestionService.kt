package com.supergrecko.questionbot.services

import com.supergrecko.questionbot.dataclasses.GuildConfig
import com.supergrecko.questionbot.dataclasses.Question
import me.aberrantfox.kjdautils.api.annotation.Service
import me.aberrantfox.kjdautils.api.dsl.embed
import me.aberrantfox.kjdautils.extensions.jda.fullName
import net.dv8tion.jda.api.entities.Guild
import java.awt.Color

/**
 * List of guilds, globally accessible
 */
private lateinit var guilds: MutableList<GuildConfig>

/**
 * Get a guild by its id
 *
 * @param id the guild snowflake
 */
fun getGuild(id: String?): GuildConfig {
    return guilds.first { it.guild == id }
}

@Service
class QuestionService(val config: ConfigService, val logger: LogService) {
    init {
        // Hacky way to extract guilds from service
        guilds = config.config.guilds
    }

    fun getQuestion(guild: Guild, id: Int) {

    }

    /**
     * Adds a question to the passed guild
     *
     * @param guild the guild to add the question to
     * @param sender the snowflake of the sender
     * @param question the question to be asked
     * @param note the note, if any
     */
    fun addQuestion(guild: Guild, sender: String, question: String, note: String = "") {
        val guildConfig = getGuild(guild.id)

        guildConfig.questions.add(Question(
                sender = sender,
                channel = guildConfig.questionChannel,
                id = guildConfig.count + 1,
                question = question,
                note = note
        ))

        guildConfig.count++
        config.save()
    }

    /**
     * Sends a question from the given guild
     *
     * @param guild the guild to send a question from
     * @param id the question id to send
     */
    fun sendQuestion(guild: Guild, id: Int) {
        val guildConfig = getGuild(guild.id)
        val question = guildConfig.questions.firstOrNull { it.id == id } ?: return
        val channel = guild.getTextChannelById(guildConfig.questionChannel) ?: guild.textChannels.first()
        val author = guild.getMemberById(question.sender) ?: return

        channel.sendMessage(embed {
            color = Color(0xfb8c00)
            thumbnail = author.user.effectiveAvatarUrl
            title = "${author.fullName()} has asked a question! (#${question.id})"
            description = question.question

            if (question.note != "") {
                addField("Notes:", question.note)
            }

            addField("How to reply:", "todo this")
        }).queue()
    }
}
