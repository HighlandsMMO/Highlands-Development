package com.highlands.highlandscore.application.players.actions

import com.highlands.highlandscore.domain.entities.Player
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class CreatePlayer(var uuid: UUID, var playerName: String) {
    init {
        transaction {
            Player.new(uuid) {
                name = playerName
            }
        }
    }
}