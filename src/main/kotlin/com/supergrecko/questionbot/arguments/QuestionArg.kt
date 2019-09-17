package com.supergrecko.questionbot.arguments

import me.aberrantfox.kjdautils.api.dsl.CommandEvent
import me.aberrantfox.kjdautils.internal.command.ArgumentResult
import me.aberrantfox.kjdautils.internal.command.ArgumentType
import me.aberrantfox.kjdautils.internal.command.ConsumptionType

open class QuestionArg(override val name: String = "Question") : ArgumentType {
    companion object : QuestionArg()

    override val examples: ArrayList<String> = arrayListOf("")
    override val consumptionType: ConsumptionType = ConsumptionType.Single
    override fun convert(arg: String, args: List<String>, event: CommandEvent): ArgumentResult {
        return ArgumentResult.Single(1)
    }
}
