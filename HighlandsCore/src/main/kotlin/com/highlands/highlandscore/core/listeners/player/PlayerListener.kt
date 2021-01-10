package com.highlands.highlandscore.core.listeners.player

import com.highlands.highlandscore.application.players.actions.CreatePlayer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerListener: Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        if (!event.player.hasPlayedBefore()) {
            event.player.sendMessage("Welcome!")
            CreatePlayer(event.player.uniqueId, event.player.displayName)

            //TODO: Add Vault Connection to Add Money on first join
        }
    }
}