package com.highlands.highlandscore.domain.entities

import com.highlands.highlandscore.domain.tables.Players
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class Player(uuid: EntityID<UUID>): UUIDEntity(uuid) {
    companion object : UUIDEntityClass<Player>(Players)

    var name by Players.name
}