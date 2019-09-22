package com.supergrecko.questionbot.commands

import com.supergrecko.questionbot.arguments.QuestionArg
import com.supergrecko.questionbot.dataclasses.Question
import com.supergrecko.questionbot.extensions.PermissionLevel
import com.supergrecko.questionbot.extensions.permission
import com.supergrecko.questionbot.services.ConfigService
import com.supergrecko.questionbot.services.QuestionService
import com.supergrecko.questionbot.tools.Arguments
import me.aberrantfox.kjdautils.api.dsl.*
import me.aberrantfox.kjdautils.internal.arguments.ChoiceArg
import me.aberrantfox.kjdautils.internal.arguments.EitherArg
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

    command("question") {
        description = "Edit or delete a question"
        requiresGuild = true
        permission = PermissionLevel.ADMIN

        expect(
                arg(ChoiceArg("ChoiceArg", "edit", "delete")),
                arg(QuestionArg),
                arg(SplitterArg, optional = true, default = "|")
        )

        execute {
            val args = Arguments(it.args)

            when (args.asType<String>(0)) {
                "edit" -> editQuestion(this, it, args, questionService)
                "delete" -> deleteQuestion(this, it, args, questionService)
            }
        }
    }
}

private fun editQuestion(cmd: Command, event: CommandEvent, args: Arguments, service: QuestionService) {
    val id = args.asType<Question>(1)
    val (question, note) = args.fromList<String>(2, 0, 1)

    service.editQuestion(event.guild!!, id!!.id, question ?: "No question was asked.", note ?: "")
    event.respond("Question #${id.id} has been edited.")
}

private fun deleteQuestion(cmd: Command, event: CommandEvent, args: Arguments, service: QuestionService) {
    val id = args.asType<Question>(1)

    service.deleteQuestion(event.guild!!, id!!.id)
    event.respond("Question #${id.id} has been deleted.")
}
