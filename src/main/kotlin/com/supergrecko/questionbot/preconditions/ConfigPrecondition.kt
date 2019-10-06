package com.supergrecko.questionbot.preconditions

import com.supergrecko.questionbot.services.ConfigService
import me.aberrantfox.kjdautils.api.dsl.Precondition
import me.aberrantfox.kjdautils.api.dsl.precondition
import me.aberrantfox.kjdautils.discord.Discord
import me.aberrantfox.kjdautils.internal.command.Fail
import me.aberrantfox.kjdautils.internal.command.Pass

@Precondition(10)
fun configIsSetup(config: ConfigService, discord: Discord) = precondition {
    val state = config.getGuild(it.guild!!.id)

    if (!state.config.channels.valid(state.guild) && it.commandStruct.commandName != "setchannel") {
        return@precondition Fail("The configuration channels have not been set up yet. Please refer to the documentation for how to set up these. <https://github.com/supergrecko/QuestionBot/blob/master/docs/command-manage.md#setchannel-admin-only>")
    }

    return@precondition Pass
}
