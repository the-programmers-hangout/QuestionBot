package com.supergrecko.questionbot.services

import com.supergrecko.questionbot.extensions.PermissionLevel
import com.supergrecko.questionbot.extensions.permission
import me.aberrantfox.kjdautils.api.annotation.Service
import me.aberrantfox.kjdautils.api.dsl.Command
import me.aberrantfox.kjdautils.discord.Discord
import me.aberrantfox.kjdautils.extensions.jda.toMember
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.User

@Service
open class VisibilityService(config: ConfigService, discord: Discord) {
    init {
        discord.configuration.visibilityPredicate = { command: Command, user: User, messageChannel: MessageChannel, guild: Guild? ->
            val isAdmin = user.toMember(guild!!)?.roles?.any { it.name == config.getGuild(guild.id).config.minRoleName }

            when {
                command.permission == PermissionLevel.ADMIN -> isAdmin != null && isAdmin == true
                else -> true
            }
        }
    }
}
