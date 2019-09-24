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

private lateinit var guilds: MutableList<GuildConfig>

@Service
class AnswerService(val config: ConfigService) {
    init {
        // Hacky way to extract guilds from service
        guilds = config.config.guilds
    }

    fun addAnswer(guild: Guild, answerDetails: AnswerDetails) {
        val state = config.getGuild(guild.id)
        val question = state.getQuestion(answerDetails.questionId)
        val answer = Answer(authorSnowflake = answerDetails.sender.id)

        question.add(answer)
        config.save()
        sendAnswer(guild, question, answer, answerDetails)
    }

    fun questionAnsweredByUser(guild: Guild, answerDetails: AnswerDetails): Boolean {
        val state = config.getGuild(guild.id)
        val question = state.getQuestion(answerDetails.questionId)
        if (question.responses.any { it.authorSnowflake == answerDetails.sender.id }) {
            return true
        }
        return false
    }

    fun editAnswer(guild: Guild, answerDetails: AnswerDetails) {
        val state = config.getGuild(guild.id)
        val question = state.getQuestion(answerDetails.questionId)
        val answerToUpdate = question.get(answerDetails.sender.id)
        val channel = guild.getTextChannelById(state.config.channels.answers) ?: guild.textChannels.first()

        config.save()
        channel.editMessageById(answerToUpdate!!.messageSnowflake, getEmbed(state, question, answerDetails)).queue()
    }

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
     * List the answers for a given question and return in an embed menu
     */
    fun listAnswers(guild: Guild, questionId: Int): Menu {
        val state = config.getGuild(guild.id)
        val question = state.getQuestion(questionId)
        val paginated = question.responses.chunked(6)

        return menu {
            paginated.forEachIndexed { index, list ->
                // Create embed for every 6 answers
                embed {
                    title = "Showing ${question.responses.size} answers for Question #${question.id}:"
                    description = question.question
                    color = Color(0xfb8c00)

                    addField("", "List of Answers:")

                    list.forEachIndexed { index, it ->
                        val author = guild.getMemberById(it.authorSnowflake)
                        val link = "https://discordapp.com/channels/${state.guild.id}/${state.config.channels.answers}/${it.messageSnowflake}"

                        addField("${author!!.fullName()}:", "[Link]($link)", true)

                        // Add blank space to maintain 3 items width in line
                        if (index == question.responses.lastIndex && question.responses.lastIndex % 3 > 0) {
                            addBlankField(true)
                        }
                    }

                    footer {
                        text = "Answer page ${index + 1} of ${paginated.size}"
                    }
                }
            }
        }
    }

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
     */
    private fun getEmbed(state: QGuild, question: Question, answerDetails: AnswerDetails) = embed {
        val link = "https://discordapp.com/channels/${state.guild.id}/${state.config.channels.questions}/${answerDetails.questionId}"

        val author = state.guild.getMemberById(answerDetails.sender.id)
        color = Color(0xfb8c00)
        title = "Answering Question #${answerDetails.questionId}:"
        description = question.question

        author {
            name = author!!.user.asTag
            iconUrl = author.user.effectiveAvatarUrl
        }

        addField("Answer:", answerDetails.text)
        addField("Link to Question", "[Link]($link)")
    }

}
