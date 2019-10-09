package com.supergrecko.questionbot.extensions

import me.aberrantfox.kjdautils.api.dsl.command.CommandEvent

fun CommandEvent<*>.invalidChannel() {
    respond("The configuration channels are invalid. Please refer to the documentation for how to set up these. <https://github.com/supergrecko/QuestionBot/blob/master/docs/command-manage.md#setchannel-admin-only>")
}
