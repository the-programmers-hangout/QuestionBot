package com.supergrecko.questionbot.services

import com.supergrecko.questionbot.dataclasses.BotConfig
import com.supergrecko.questionbot.dataclasses.GuildConfig
import com.supergrecko.questionbot.dataclasses.GuildImpl
import com.supergrecko.questionbot.dataclasses.LogChannels
import me.aberrantfox.kjdautils.api.annotation.Service
import me.aberrantfox.kjdautils.discord.Discord
import me.aberrantfox.kjdautils.internal.di.PersistenceService
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.TextChannel

private lateinit var guilds: MutableList<GuildConfig>

fun guildFromId(id: String?): GuildConfig {
    return guilds.first { it.guildSnowflake == id }
}

@Service
open class ConfigService(val config: BotConfig, private val discord: Discord, private val store: PersistenceService) {
    init {
        guilds = config.guilds
    }

    fun setAdminRole(guild: String, role: String) {
        getGuild(guild).config.minRoleName = role

        save()
    }

    fun getGuild(guild: String) = GuildImpl(
            guild = discord.jda.getGuildById(guild)!!,
            config = guildFromId(guild)
    )

    fun setChannel(name: LogChannels, guild: String, channel: TextChannel) {
        val settings = guildFromId(guild).channels

        when (name) {
            LogChannels.ANSWER -> settings.answers = channel.id
            LogChannels.QUESTION -> settings.questions = channel.id
            LogChannels.LOG -> settings.logs = channel.id
        }

        save()
    }

    fun enableLogging(guild: String, enabled: Boolean) {
        guildFromId(guild).loggingEnabled = enabled
        save()
    }

    fun save() = store.save(config)

    fun registerGuilds() {
        // TODO: find better algo than O(n^2)
        val unsaved = discord.jda.guilds.filter { guild ->
            config.guilds.none { it.guildSnowflake == guild.id }
        }

        unsaved.forEach {
            config.guilds.add(GuildConfig(
                    guildSnowflake = it.id,
                    minRoleName = "Staff"
            ))
        }

        save()
    }

    fun unregister(guild: Guild) {
        val guilds = config.guilds.filter { it.guildSnowflake == guild.id }

        guilds.forEach { config.removeGuild(it.guildSnowflake) }
        save()
    }
}
