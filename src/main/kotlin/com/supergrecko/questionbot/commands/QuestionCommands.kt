package com.supergrecko.questionbot.commands

import com.supergrecko.questionbot.arguments.QuestionArg
import com.supergrecko.questionbot.dataclasses.Question
import com.supergrecko.questionbot.extensions.PermissionLevel
import com.supergrecko.questionbot.extensions.permission
import com.supergrecko.questionbot.services.ConfigService
import com.supergrecko.questionbot.services.QuestionService
import me.aberrantfox.kjdautils.api.dsl.CommandSet
import me.aberrantfox.kjdautils.api.dsl.commands
import me.aberrantfox.kjdautils.api.dsl.embed
import me.aberrantfox.kjdautils.internal.arguments.SentenceArg
import me.aberrantfox.kjdautils.internal.arguments.SplitterArg
import me.aberrantfox.kjdautils.internal.arguments.TextChannelArg
import net.dv8tion.jda.api.entities.TextChannel
import java.awt.Color

@CommandSet("ask")
fun questionCommands(config: ConfigService, questionService: QuestionService) = commands {
    command("ask") {
        description = "Ask the channel a question."
        requiresGuild = true
        permission = PermissionLevel.ADMIN

        expect(SplitterArg)

        execute {
            // Question and Note args
            val question = (it.args.first() as List<*>).getOrNull(0) as? String
            val note = (it.args.first() as List<*>).getOrNull(1) as? String

            val guild = config.config.guilds.first { c -> c.guild == it.guild?.id }

            // Add question and send it
            questionService.addQuestion(it.guild!!, it.author.id, question ?: "No Question", note ?: "")
            questionService.sendQuestion(it.guild!!, guild.count)
            it.respond("Your question has been asked.")
        }
    }

    command("edit") {
        description = "Edit a question"
        requiresGuild = true
        permission = PermissionLevel.ADMIN

        expect(QuestionArg, SplitterArg)

        execute {
            val question = (it.args.first() as Question)
            val newText = (it.args.last() as List<*>).getOrNull(0) as? String
            val newNote = (it.args.last() as List<*>).getOrNull(1) as? String

            questionService.editQuestion(it.guild!!, question.id, newText!!, newNote ?: "")
            it.respond("Question #${question.id} has been edited.")
        }
    }

    command("delete") {
        description = "Delete a question"
        requiresGuild = true
        permission = PermissionLevel.ADMIN

        expect(QuestionArg)

        execute {
        }
    }
}
