package com.supergrecko.questionbot.services

import com.supergrecko.questionbot.dataclasses.Answer
import com.supergrecko.questionbot.dataclasses.AnswerDetails
import com.supergrecko.questionbot.dataclasses.GuildConfig
import com.supergrecko.questionbot.dataclasses.Question
import me.aberrantfox.kjdautils.api.annotation.Service
import me.aberrantfox.kjdautils.api.dsl.Menu
import me.aberrantfox.kjdautils.api.dsl.embed
import me.aberrantfox.kjdautils.api.dsl.menu
import me.aberrantfox.kjdautils.extensions.jda.fullName
import net.dv8tion.jda.api.entities.Guild
import java.awt.Color

/**
 * List of guilds, globally accessible
 */
private lateinit var guilds: MutableList<GuildConfig>

@Service
class AnswerService(val config: ConfigService) {
    init {
        // Hacky way to extract guilds from service
        guilds = config.config.guilds
    }

    /**
     * Adds a question to the passed guild
     *
     * @param guild the guild to add the question to
     * @param answerDetails the answer details for the answer
     */
    fun addAnswer(guild: Guild, answerDetails: AnswerDetails) {
        val state = config.getGuild(guild.id)
        val question = state.getQuestion(answerDetails.questionId)
        val answer = Answer(authorSnowflake = answerDetails.sender.id, invocationSnowflake = answerDetails.invocationId)

        question.add(answer)
        config.save()
        sendAnswer(guild, question, answer, answerDetails)
    }

    /**
     * Check if a user has already answered a given question
     *
     * @param guild the guild to add the question to
     * @param answerDetails the answer details for the answer
     */

    fun questionAnsweredByUser(guild: Guild, answerDetails: AnswerDetails): Boolean {
        val state = config.getGuild(guild.id)
        val question = state.getQuestion(answerDetails.questionId)
        if (question.responses.any { it.authorSnowflake == answerDetails.sender.id }) {
            return true
        }
        return false
    }

    /**
     * Edits an answer with the given answer details
     *
     * @param guild the guild to send a question from
     * @param answerDetails the answer details for the answer
     */
    fun editAnswer(guild: Guild, answerDetails: AnswerDetails) {
        val state = config.getGuild(guild.id)
        val question = state.getQuestion(answerDetails.questionId)
        val answerToUpdate = question.get(answerDetails.sender.id)
        val channel = guild.getTextChannelById(state.config.channels.answers) ?: guild.textChannels.first()

        answerToUpdate?.setInvocationId(answerDetails.invocationId)
        config.save()

        channel.editMessageById(answerToUpdate!!.messageSnowflake, getEmbed(state, question, answerDetails)).queue()
    }

    /**
     * Deletes an answer to a given question from the given guild
     *
     * @param guild the guild to delete from
     * @param answerDetails the answer details for the answer
     */
    fun deleteAnswer(guild: Guild, answerDetails: AnswerDetails) {
        val state = config.getGuild(guild.id)
        val question = state.getQuestion(answerDetails.questionId)
        val answerToDelete = question.get(answerDetails.sender.id)
        val channel = guild.getTextChannelById(state.config.channels.answers) ?: guild.textChannels.first()

        question.deleteBy(answerDetails.sender.id)
        config.save()

        channel.deleteMessageById(answerToDelete!!.messageSnowflake).queue()
    }

    /**
     * List the answers for a given question and return in an embed
     *
     * @param guild the guild to send a question from
     * @param questionId the question id to send
     */
    fun listAnswers(guild: Guild, questionId: Int): Menu {
        val state = config.getGuild(guild.id)
        val question = state.getQuestion(questionId)
        val paginated = question.responses.chunked(6)

        return menu {
            paginated.forEachIndexed { index, list ->
                embed {
                    title = "Showing ${question.responses.size} answers for Question #${question.id}:"
                    description = "${question.question}"
                    color = Color(0xfb8c00)

                    addField("", "List of Answers:")
                    footer {
                        text = "Answer page ${index + 1} of ${paginated.size}"
                    }
                    list.forEachIndexed { index, it ->
                        val author = guild.getMemberById(it.authorSnowflake)
                        val link = "https://discordapp.com/channels/${state.guild.id}/${state.config.channels.answers}/${it.messageSnowflake}"
                        addField("${author!!.fullName()}:", "[Link]($link)", true)

                        // Add blank space to maintain 3 items width in line
                        if (index == question.responses.lastIndex && question.responses.lastIndex % 3 > 0) addBlankField(true)
                    }
                }
            }
        }
    }

    /**
     * Sends an answer for the given question
     *
     * @param guild the guild to send a question from
     * @param question the question to answer
     * @param answer the answer to reply with
     * @param answerDetails the answer details
     */
    private fun sendAnswer(guild: Guild, question: Question, answer: Answer, answerDetails: AnswerDetails) {
        val state = config.getGuild(guild.id)
        val channel = guild.getTextChannelById(state.config.channels.answers) ?: guild.textChannels.first()

        channel.sendMessage(getEmbed(state, question, answerDetails)).queue {
            it.addReaction("\uD83D\uDC4D").queue()
            it.addReaction("\uD83D\uDC4E").queue()
            answer.setEmbedId(it.id)
            config.save()
        }
    }

    /**
     * Generate the RichEmbed for an answer
     *
     * @param state the guild to pull data from
     * @param answerDetails the question
     */
    private fun getEmbed(state: QGuild, question: Question, answerDetails: AnswerDetails) = embed {
        val link = "https://discordapp.com/channels/${state.guild.id}/${state.config.channels.questions}/${answerDetails.questionId}"
        val author = state.guild.getMemberById(answerDetails.sender.id)
        color = Color(0xfb8c00)
        title = "Answering question #${answerDetails.questionId}:"
        description = question.question
        author {
            name = author!!.user.asTag
            iconUrl = author.user.effectiveAvatarUrl
        }

        addBlankField(false)
        addField("Answer:", answerDetails.text)
        addBlankField(false)
        addField("Link to Question", "[Link]($link)")
    }

}
