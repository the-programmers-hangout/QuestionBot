package com.supergrecko.questionbot.services

import me.aberrantfox.kjdautils.api.annotation.Service
import me.aberrantfox.kjdautils.api.dsl.embed
import net.dv8tion.jda.api.entities.Guild
import java.awt.Color

@Service
class InfoService {
    // TODO: complete embed
    fun infoEmbed(guild: Guild) = embed {
        color = Color(0x00bfff)
        addField("This is a field", "Stuff is here")
    }
}
