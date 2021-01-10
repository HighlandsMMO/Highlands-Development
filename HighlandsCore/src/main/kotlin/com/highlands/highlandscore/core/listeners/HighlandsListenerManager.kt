package com.highlands.highlandscore.core.listeners

import com.highlands.highlandscore.core.HighlandsCore
import com.highlands.highlandscore.core.listeners.player.PlayerListener
import org.bukkit.event.Listener
import java.util.logging.Level

class HighlandsListenerManager(var highlandsCore: HighlandsCore) {
    lateinit var listeners: List<Listener>

    init {
        listeners = listOf(
            PlayerListener()
        )

        for (listener in listeners) {
            highlandsCore.advancedLogger?.Log(Level.INFO, "HighlandsCore", "Registering Events for HighlandsCore.")
            highlandsCore.server.pluginManager.registerEvents(listener, highlandsCore)
        }
    }
}