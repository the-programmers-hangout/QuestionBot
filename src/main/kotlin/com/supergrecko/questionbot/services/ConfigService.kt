package com.supergrecko.questionbot.services

import com.supergrecko.questionbot.dataclasses.BotConfig
import com.supergrecko.questionbot.dataclasses.GuildConfig
import com.supergrecko.questionbot.services.getGuild as getConfig
import me.aberrantfox.kjdautils.api.annotation.Service
import me.aberrantfox.kjdautils.discord.Discord
import me.aberrantfox.kjdautils.internal.di.PersistenceService
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.internal.entities.TextChannelImpl

data class QGuild(
        val config: GuildConfig,
        val guild: Guild
) {
    fun getQuestion(id: Int) = config.questions.first { it.id == id }
}

@Service
open class ConfigService(val config: BotConfig, private val discord: Discord, private val store: PersistenceService) {
    /**
     * Set the bot prefix
     *
     * @param prefix the new prefix
     */
    fun setPrefix(prefix: String = config.prefix) {
        config.prefix = prefix
        discord.configuration.prefix = prefix
    }

    fun getGuild(guild: String): QGuild {
        return QGuild(
                guild = discord.jda.getGuildById(guild)!!,
                config = getConfig(guild)
        )
    }

    /**
     * Set the log channel to be used
     *
     * @param guild the guild to edit
     * @param channel the text channel
     */
    fun setLogChannel(guild: String, channel: TextChannelImpl) {
        getConfig(guild).channels.logs = channel.id
        save()
    }

    /**
     * Set the question channel to be used
     *
     * @param guild the guild to edit
     * @param channel the text channel
     */
    fun setQuestionChannel(guild: String, channel: TextChannelImpl) {
        getConfig(guild).channels.questions = channel.id
        save()
    }

    /**
     * Set logging to on or off
     *
     * @param guild the id of the guild
     * @param enabled on / off to enable or disable logging
     */
    fun enableLogging(guild: String, enabled: Boolean) {
        getConfig(guild).loggingEnabled = enabled
        save()
    }

    /**
     * Alias PersistenceService#save
     */
    fun save() = store.save(config)

    /**
     * Registers all the unregistered servers
     *
     * This works by grabbing every single guild the
     * bot is in and testing whether they're in the
     * config or not.
     */
    fun registerGuilds() {
        // TODO: find better algo than O(n^2)
        val unsaved = discord.jda.guilds.filter { guild ->
            config.guilds.none { it.guild == guild.id }
        }

        unsaved.forEach {
            config.guilds.add(GuildConfig(
                    guild = it.id,
                    role = "Staff"
            ))
        }
    }

    /**
     * Removes the given guild id from the config
     *
     * @param guild the guild snowflake
     */
    fun unregister(guild: String) {
        val item = config.guilds.filter { it.guild == guild }

        config.guilds.removeAll(item)
        save()
    }
}
