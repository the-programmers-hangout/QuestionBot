package com.supergrecko.questionbot.services

import com.supergrecko.questionbot.dataclasses.GuildConfig
import com.supergrecko.questionbot.dataclasses.Question
import me.aberrantfox.kjdautils.api.annotation.Service
import me.aberrantfox.kjdautils.api.dsl.embed
import me.aberrantfox.kjdautils.discord.Discord
import net.dv8tion.jda.api.entities.Guild
import java.awt.Color

private lateinit var guilds: MutableList<GuildConfig>

/**
 * Get a guild by its id
 *
 * @param id the guild snowflake
 */
fun getGuild(id: String?): GuildConfig {
    return guilds.first { it.guildSnowflake == id }
}

@Service
class QuestionService(val config: ConfigService, private val discord: Discord) {
    init {
        // Hacky way to extract guilds from service
        guilds = config.config.guilds
    }

    fun setPrefix(prefix: String, guild: Guild) {
        config.config.prefix = prefix
        discord.configuration.prefix = prefix

        updateQuestions(guild)

        config.save()
    }

    fun addQuestion(guild: Guild, sender: String, question: String, note: String = "") {
        val state = config.getGuild(guild.id)

        state.config.addQuestion(Question(
                authorSnowflake = sender,
                channelSnowflake = state.config.channels.questions,
                id = state.config.count + 1,
                question = question,
                note = note
        ))

        state.config.count++
        config.save()
    }

    /**
     * Edits a question with the given guild and sends updated question to the given guild
     */
    fun editQuestion(guild: Guild, id: Int, newQuestion: String, newNote: String) {
        val state = config.getGuild(guild.id)
        val question = state.getQuestion(id)

        val channel = guild.getTextChannelById(state.config.channels.questions) ?: guild.textChannels.first()

        question.edit(newQuestion, newNote)
        config.save()

        channel.editMessageById(question.messageSnowflake, getEmbed(state, question)).queue()
    }

    fun deleteQuestion(guild: Guild, id: Int, orElse: () -> Unit = {}) {
        val state = config.getGuild(guild.id)
        val question = state.getQuestion(id)

        state.guild.getTextChannelById(question.channelSnowflake)!!.deleteMessageById(question.messageSnowflake).queue()
        question.responses.forEach { it.deleteMessage(guild.getTextChannelById(state.config.channels.answers)!!)}

        if (state.config.count == id) {
            state.config.count--
        }

        state.config.deleteQuestion(question, orElse)
        config.save()
    }

    fun updateQuestions(guild: Guild) {
        val state = config.getGuild(guild.id)
        state.config.questions.forEach {
            val channel = guild.getTextChannelById(state.config.channels.questions) ?: guild.textChannels.first()

            channel.editMessageById(it.messageSnowflake, getEmbed(state, it)).queue()
        }
    }

    fun sendQuestion(guild: Guild, id: Int) {
        val state = config.getGuild(guild.id)
        val question = state.getQuestion(id)

        // This is always valid because the precondition checks the channel's existence
        val channel = guild.getTextChannelById(state.config.channels.questions)!!

        channel.sendMessage(getEmbed(state, question)).queue {
            state.getQuestion(id).messageSnowflake = it.id
            config.save()
        }
    }

    /**
     * Generates the RichEmbed for the given question
     */
    private fun getEmbed(state: QGuild, question: Question) = embed {
        val author = state.guild.getMemberById(question.authorSnowflake)!!

        color = Color(0xfb8c00)
        title = "${question.question} (#${question.id})"

        description = question.note

        addField("How do I reply?", "You can answer questions by invoking the `${config.config.prefix}answer` command in the bot commands channel.")
        addField("Example","${config.config.prefix}answer ${question.id} This my answer to the question")

        author {
            name = author.user.asTag
            iconUrl = author.user.effectiveAvatarUrl
        }
    }

}
