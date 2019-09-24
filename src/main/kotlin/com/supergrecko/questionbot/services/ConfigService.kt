package com.supergrecko.questionbot.services

import com.supergrecko.questionbot.dataclasses.BotConfig
import com.supergrecko.questionbot.dataclasses.GuildConfig
import com.supergrecko.questionbot.services.getGuild as getConfig
import me.aberrantfox.kjdautils.api.annotation.Service
import me.aberrantfox.kjdautils.discord.Discord
import me.aberrantfox.kjdautils.internal.di.PersistenceService
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.internal.entities.TextChannelImpl

enum class LogChannels {
    QUESTION,
    ANSWER,
    LOG
}

data class QGuild(
        val config: GuildConfig,
        val guild: Guild
) {
    fun getQuestion(id: Int) = config.questions.first { it.id == id }
}

@Service
open class ConfigService(val config: BotConfig, private val discord: Discord, private val store: PersistenceService) {

    // TODO: update question embeds
    fun setPrefix(prefix: String = config.prefix) {
        config.prefix = prefix
        discord.configuration.prefix = prefix

        save()
    }

    fun setAdminRole(guild: String, role: String) {
        getGuild(guild).config.minRoleName = role

        save()
    }

    fun getGuild(guild: String) = QGuild(
            guild = discord.jda.getGuildById(guild)!!,
            config = getConfig(guild)
    )

    fun setChannel(name: LogChannels, guild: String, channel: TextChannelImpl) {
        val settings = getConfig(guild).channels

        when (name) {
            LogChannels.ANSWER -> settings.answers = channel.id
            LogChannels.QUESTION -> settings.questions = channel.id
            LogChannels.LOG -> settings.logs = channel.id
        }

        save()
    }

    fun enableLogging(guild: String, enabled: Boolean) {
        getConfig(guild).loggingEnabled = enabled
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
