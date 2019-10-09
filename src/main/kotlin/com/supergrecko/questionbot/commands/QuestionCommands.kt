package com.supergrecko.questionbot.commands

import com.supergrecko.questionbot.arguments.QuestionArg
import com.supergrecko.questionbot.extensions.PermissionLevel
import com.supergrecko.questionbot.extensions.permission
import com.supergrecko.questionbot.services.ConfigService
import com.supergrecko.questionbot.services.QuestionService
import me.aberrantfox.kjdautils.api.dsl.command.CommandSet
import me.aberrantfox.kjdautils.api.dsl.command.commands
import me.aberrantfox.kjdautils.internal.arguments.ChoiceArg
import me.aberrantfox.kjdautils.internal.arguments.SplitterArg

@CommandSet("Ask")
fun questionCommands(config: ConfigService, questionService: QuestionService) = commands {
    command("ask") {
        description = "Ask the channel a question."
        permission = PermissionLevel.ADMIN

        execute(SplitterArg) {
            val state = config.getGuild(it.guild!!.id)
            val question = it.args.first.first()
            val note = it.args.first.getOrNull(1) ?: ""

            // Add question and send it
            questionService.addQuestion(it.guild!!, it.author.id, question, note)
            questionService.sendQuestion(it.guild!!, state.config.count)
            it.respond("Your question has been asked.")
        }
    }

    command("Question") {
        description = "Edit or delete a question"
        permission = PermissionLevel.ADMIN

        execute(ChoiceArg("action", "edit", "delete"), QuestionArg, SplitterArg.makeOptional(listOf("|"))) {
            when (it.args.first) {
                "edit" -> {
                    val id = it.args.second
                    val (question, note) = it.args.third

                    questionService.editQuestion(it.guild!!, id.id, question ?: "No question was asked.", note ?: "")

                    it.respond("Question #${id.id} has been edited.")
                }
                "delete" -> {
                    val id = it.args.second

                    questionService.deleteQuestion(it.guild!!, id.id) {
                        it.respond("Question #${id.id} could not be found.")

                        // Yes, this requires a label to work
                        return@deleteQuestion
                    }

                    it.respond("Question #${id.id} has been deleted.")
                }
            }
        }
    }
}
