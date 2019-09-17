package com.supergrecko.questionbot.arguments

import com.supergrecko.questionbot.services.getGuild
import me.aberrantfox.kjdautils.api.dsl.CommandEvent
import me.aberrantfox.kjdautils.internal.command.ArgumentResult
import me.aberrantfox.kjdautils.internal.command.ArgumentType
import me.aberrantfox.kjdautils.internal.command.ConsumptionType

/**
 * Question argument for commands
 *
 * @param name optional override name
 */
open class QuestionArg(override val name: String = "Question") : ArgumentType {
    companion object : QuestionArg()

    override val examples: ArrayList<String> = arrayListOf("1", "23", "16")

    override val consumptionType: ConsumptionType = ConsumptionType.Single

    override fun convert(arg: String, args: List<String>, event: CommandEvent): ArgumentResult {
        val config = getGuild(event.guild?.id)

        val question = config.questions.firstOrNull { it.id.toString() == args.first() }

        return if (question == null)
            ArgumentResult.Error("Question with id (${args.first()}) does not exist in this guild.") else
            ArgumentResult.Single(question)
    }
}
