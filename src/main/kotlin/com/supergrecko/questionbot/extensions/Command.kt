package com.supergrecko.questionbot.extensions

import me.aberrantfox.kjdautils.api.dsl.Command

/**
 * Global map to keep track of Command to Permission levels
 */
val permissions: MutableMap<Command, PermissionLevel> = mutableMapOf()

/**
 * @property EVERYONE anyone can use this
 * @property ADMIN only people with config role can use this
 */
enum class PermissionLevel(val value: Int) {
    EVERYONE(0),
    ADMIN(10)
}

/**
 * Allow setting .permission on DSL
 */
var Command.permission: PermissionLevel
    get() = permissions[this] ?: PermissionLevel.EVERYONE
    set(value) { permissions[this] = value }
