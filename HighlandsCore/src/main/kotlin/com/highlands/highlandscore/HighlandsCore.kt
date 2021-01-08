package com.highlands.highlandscore

import com.highlands.highlandscore.api.HighlandsCoreApi
import com.highlands.highlandscore.commands.HighlandsCommandHandler
import com.highlands.highlandscore.infrastructure.HighlandsCoreDB
import com.highlands.highlandscore.settings.Settings
import com.highlands.highlandscore.utilities.AdvancedLogger
import kr.entree.spigradle.annotations.PluginMain
import org.bukkit.plugin.java.JavaPlugin
import java.sql.SQLException
import java.util.logging.Level

@PluginMain
class HighlandsCore : JavaPlugin() {

    //Settings
    var settings: Settings? = null
        private set

    //Loggers
    var advancedLogger: AdvancedLogger? = null
        private set

    //Database
    var playerDataDB: HighlandsCoreDB? = null
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
        playerDataDB = HighlandsCoreDB()
        if (settings!!.useMysql) playerDataDB!!.setupMysql(this) else playerDataDB!!.setupSQLite(this)
        try {
            playerDataDB!!.connect()
        } catch (e: SQLException) {
            advancedLogger!!.Log(Level.SEVERE, "HighlandsCore", e.message)
        }

        //Api
        highlandsCoreApi = HighlandsCoreApi(this)

        //Command
        registerCommands()
    }

    override fun onDisable() {
        try {
            playerDataDB!!.disconnect()
        } catch (e: SQLException) {
            advancedLogger!!.Log(Level.SEVERE, "HighlandsCore", e.message)
        }
    }

    fun reloadPluginConfig() {
        reloadConfig()
    }

    private fun registerCommands() {
        getCommand("hlc")!!.setExecutor(HighlandsCommandHandler())
    }
}