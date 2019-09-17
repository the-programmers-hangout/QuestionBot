package com.supergrecko.questionbot.commands

import com.supergrecko.questionbot.services.ConfigService
import me.aberrantfox.kjdautils.api.dsl.CommandSet
import me.aberrantfox.kjdautils.api.dsl.commands
import me.aberrantfox.kjdautils.api.dsl.embed
import me.aberrantfox.kjdautils.internal.arguments.RoleArg
import me.aberrantfox.kjdautils.internal.arguments.WordArg
import java.awt.Color
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@CommandSet("config")
fun adminCommands(config: ConfigService) = commands {
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

    command("setprefix") {
        description = "Sets the bot prefix."
        requiresGuild = true

        expect(WordArg)

        execute {
            config.setPrefix(it.args.first() as String)
            config.save()

            it.respond(embed {
                color = Color(0xfb8c00)
                title = "Success!"
                description = "Bot Prefix has successfully been updated."

                addInlineField("New Prefix", it.args.first() as String)
                addInlineField("Invoked By", it.author.name)
                addInlineField("Date", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE))
            })
        }
    }
}
