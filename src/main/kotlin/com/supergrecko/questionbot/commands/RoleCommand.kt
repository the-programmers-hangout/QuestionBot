package com.supergrecko.questionbot.commands

import me.aberrantfox.kjdautils.api.dsl.CommandSet
import me.aberrantfox.kjdautils.api.dsl.commands
import me.aberrantfox.kjdautils.internal.arguments.RoleArg

@CommandSet("config")
fun roleCommand() = commands {
    command("setrole") {
        description = "Set the lowest required role to invoke commands."
        requiresGuild = true
        expect(RoleArg)

        execute {
            val (role) = it.args

            // TODO: Implement it
            it.respond(role.toString())
        }
    }
}
