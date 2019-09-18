package com.supergrecko.questionbot.services

import com.supergrecko.questionbot.dataclasses.BotConfig
import com.supergrecko.questionbot.dataclasses.GuildConfig
import me.aberrantfox.kjdautils.api.annotation.Service
import me.aberrantfox.kjdautils.discord.Discord
import me.aberrantfox.kjdautils.internal.arguments.OnOffArg
import me.aberrantfox.kjdautils.internal.arguments.YesNoArg
import me.aberrantfox.kjdautils.internal.di.PersistenceService

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

    /**
     * Set the log channel to be used
     *
     * @param channelId the channel id to be used
     */
    fun setLogChannel(guild: String, channelId: String) {
        config.guilds.find { it.guild == guild }?.logChannel = channelId
    }
    /**
     * Set the log channel to be used
     *
     * @param guildId the id of the guild
     * @param enabled on / off to enable or disable logging
     */
    fun enableLogging(guildId: String, enabled: Boolean) {
        config.guilds.find { it.guild == guildId }?.loggingEnabled = enabled
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
            config.guilds.filter { it.guild == guild.id }.isEmpty()
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
    }
}
