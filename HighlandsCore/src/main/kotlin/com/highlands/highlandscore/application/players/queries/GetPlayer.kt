package com.highlands.highlandscore.application.players.queries

import com.highlands.highlandscore.domain.entities.Player
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class GetPlayer(uuid: UUID) {
    init {
        transaction {
            val player = Player.findById(uuid)

            System.out.println(player?.name)
        }
    }
}