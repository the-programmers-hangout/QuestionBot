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
class QuestionService(val config: ConfigService) {
    init {
        // Hacky way to extract guilds from service
        guilds = config.config.guilds
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
        val state = config.getGuild(guild.id)

        state.config.addQuestion(Question(
                sender = sender,
                channel = state.config.channels.questions,
                id = state.config.count + 1,
                question = question,
                note = note
        ))

        state.config.count++
        config.save()
    }

    /**
     * Edits a question with the given guild and sends updated question to the given guild
     *
     * @param guild the guild to send a question from
     * @param newQuestion the new question text
     * @param newNote the new question note
     */
    fun editQuestion(guild: Guild, id: Int, newQuestion: String, newNote: String) {
        val state = config.getGuild(guild.id)
        val question = state.getQuestion(id)

        val channel = guild.getTextChannelById(state.config.channels.questions) ?: guild.textChannels.first()

        question.update(newQuestion, newNote)
        config.save()

        channel.editMessageById(question.message, getEmbed(state, question)).queue()
    }

    /**
     * Sends a question from the given guild
     *
     * @param guild the guild to send a question from
     * @param id the question id to send
     */
    fun sendQuestion(guild: Guild, id: Int) {
        val state = config.getGuild(guild.id)
        val question = state.getQuestion(id)

        val channel = guild.getTextChannelById(state.config.channels.questions) ?: guild.textChannels.first()

        channel.sendMessage(getEmbed(state, question)).queue {
            state.getQuestion(id).message = it.id
            config.save()
        }
    }

    /**
     * Generate the RichEmbed for the given question
     *
     * @param state the guild to pull data from
     * @param question the question
     */
    private fun getEmbed(state: QGuild, question: Question) = embed {
        val author = state.guild.getMemberById(question.sender)!!
        val askChannel = state.guild.getTextChannelById(state.config.channels.questions) ?: state.guild.textChannels.first()

        color = Color(0xfb8c00)
        thumbnail = author.user.effectiveAvatarUrl
        title = "${author.fullName()} has asked a question! (#${question.id})"
        description = question.question

        if (question.note != "") {
            addField("Notes:", question.note)
        }

        addField("How to reply:", "Invoke ${config.config.prefix}answer with the question id in ${askChannel.asMention}")
        addField("Example","${config.config.prefix}answer ${question.id} This my answer to the question")
    }

}
