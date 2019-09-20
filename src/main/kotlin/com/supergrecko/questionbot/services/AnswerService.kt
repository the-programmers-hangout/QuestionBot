package com.supergrecko.questionbot.services

import com.supergrecko.questionbot.dataclasses.Answer
import com.supergrecko.questionbot.dataclasses.AnswerDetails
import com.supergrecko.questionbot.dataclasses.GuildConfig
import com.supergrecko.questionbot.dataclasses.Question
import me.aberrantfox.kjdautils.api.annotation.Service
import me.aberrantfox.kjdautils.api.dsl.embed
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
        val answer = Answer(sender = answerDetails.sender.id, invocation = answerDetails.invocationId)

        question.addAnswer(answer)
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
        if (question.responses.any { it.sender == answerDetails.sender.id }) {
            return true
        }
        return false
    }

    /**
     * Edits a question with the given guild and sends updated question to the given guild
     *
     * @param guild the guild to send a question from
     * @param newText the new answer text
     * @param embedId the id of the embed in the answers channel
     */
    fun editAnswer(guild: Guild, id: Int, newText: String, embedId: String) {

    }

    /**
     * Sends a question from the given guild
     *
     * @param guild the guild to send a question from
     * @param id the question id to send
     */
    private fun sendAnswer(guild: Guild, question: Question, answer: Answer, answerDetails: AnswerDetails) {
        val state = config.getGuild(guild.id)
        val channel = guild.getTextChannelById(state.config.channels.answers) ?: guild.textChannels.first()

        channel.sendMessage(getEmbed(state, answerDetails)).queue {
            it.addReaction("\uD83D\uDC4D").queue()
            it.addReaction("\uD83D\uDC4E").queue()
            answer.setEmbedId(it.id)
            config.save()
        }
    }

    /**
     * Generate the RichEmbed for the given question
     *
     * @param state the guild to pull data from
     * @param question the question
     */
    private fun getEmbed(state: QGuild, answerDetails: AnswerDetails) = embed {
        val linkToQuestion = "https://discordapp.com/channels/${state.guild?.id}/${state.config.channels.questions}/${answerDetails.questionId}"

        color = Color(0xfb8c00)
        title = "${answerDetails.sender.asTag} has answered question (#${answerDetails.questionId})!"
        description = answerDetails.text
        addField("Question", linkToQuestion)
    }

}
