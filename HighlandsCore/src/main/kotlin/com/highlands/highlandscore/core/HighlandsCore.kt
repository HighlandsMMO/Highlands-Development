package com.highlands.highlandscore.core

import com.highlands.highlandscore.core.api.HighlandsCoreApi
import com.highlands.highlandscore.application.players.actions.CreatePlayer
import com.highlands.highlandscore.application.players.queries.GetPlayer
import com.highlands.highlandscore.core.commands.HighlandsCommandHandler
import com.highlands.highlandscore.core.listeners.HighlandsListenerManager
import com.highlands.highlandscore.infrastructure.HighlandsContext
import com.highlands.highlandscore.core.settings.Settings
import com.highlands.highlandscore.core.utilities.AdvancedLogger
import kr.entree.spigradle.annotations.PluginMain
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

@PluginMain
class HighlandsCore : JavaPlugin() {

    //Settings
    var settings: Settings? = null
        private set

    //Loggers
    var advancedLogger: AdvancedLogger? = null
        private set

    //Database
    var highlandsContext: HighlandsContext? = null
        private set
    var highlandsCoreApi: HighlandsCoreApi? = null
        private set

    override fun onEnable() {
        //Settings
        saveDefaultConfig()
        settings = Settings(this)

        //Loggers
        advancedLogger = AdvancedLogger(this)

        //DB
        highlandsContext = HighlandsContext(this)

        //Api
        highlandsCoreApi = HighlandsCoreApi(this)

        //Command
        registerCommands()

        //Listeners
        registerListeners()
    }

    override fun onDisable() {
        
    }

    fun reloadPluginConfig() {
        reloadConfig()
    }

    private fun registerCommands() {
        getCommand("hlc")!!.setExecutor(HighlandsCommandHandler())
    }

    private fun registerListeners() {
        HighlandsListenerManager(this)
    }
}