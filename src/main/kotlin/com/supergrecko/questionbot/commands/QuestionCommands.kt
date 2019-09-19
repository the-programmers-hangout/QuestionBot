package com.supergrecko.questionbot.commands

import com.supergrecko.questionbot.arguments.QuestionArg
import com.supergrecko.questionbot.dataclasses.Question
import com.supergrecko.questionbot.extensions.PermissionLevel
import com.supergrecko.questionbot.extensions.permission
import com.supergrecko.questionbot.services.ConfigService
import com.supergrecko.questionbot.services.QuestionService
import com.supergrecko.questionbot.tools.Arguments
import me.aberrantfox.kjdautils.api.dsl.CommandSet
import me.aberrantfox.kjdautils.api.dsl.commands
import me.aberrantfox.kjdautils.internal.arguments.SplitterArg

@CommandSet("ask")
fun questionCommands(config: ConfigService, questionService: QuestionService) = commands {
    command("ask") {
        description = "Ask the channel a question."
        requiresGuild = true
        permission = PermissionLevel.ADMIN

        expect(SplitterArg)

        execute {
            val state = config.getGuild(it.guild!!.id)
            val args = Arguments(it.args)

            val (question, note) = args.fromList<String>(0, 0, 1)

            // Add question and send it
            questionService.addQuestion(it.guild!!, it.author.id, question ?: "No Question", note ?: "")
            questionService.sendQuestion(it.guild!!, state.config.count)
            it.respond("Your question has been asked.")
        }
    }

    command("edit") {
        description = "Edit a question"
        requiresGuild = true
        permission = PermissionLevel.ADMIN

        expect(QuestionArg, SplitterArg)

        execute {
            val args = Arguments(it.args)
            val id = args.asType<Question>(0)
            val (question, note) = args.fromList<String>(1, 0, 1)

            questionService.editQuestion(it.guild!!, id!!.id, question ?: "No question was asked.", note ?: "")
            it.respond("Question #${id.id} has been edited.")
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
