package com.supergrecko.questionbot.preconditions

import com.supergrecko.questionbot.extensions.PermissionLevel
import com.supergrecko.questionbot.extensions.permission
import com.supergrecko.questionbot.services.ConfigService
import me.aberrantfox.kjdautils.api.dsl.Precondition
import me.aberrantfox.kjdautils.api.dsl.precondition
import me.aberrantfox.kjdautils.internal.command.Fail
import me.aberrantfox.kjdautils.internal.command.Pass

/**
 * Determines whether a user can invoke a command or not
 * based on their role and the commands permission level
 *
 * @param config dependency injected config
 */
@Precondition
fun canInvoke(config: ConfigService) = precondition {
    val role = config.config.guilds.first { e -> e.guild == it.guild!!.id }.role
    // Determine if user has role
    val admin = it.message.member?.roles?.filter { r -> r.name == role }?.isNotEmpty()

    val perm: PermissionLevel = if (admin != true)
        PermissionLevel.EVERYONE else
        PermissionLevel.ADMIN

    // If your permission level is higher or equal command level
    if (perm >= it.container[it.commandStruct.commandName]!!.permission) {
        return@precondition Pass
    }

    return@precondition Fail("You do not have access to use this command.")
}
