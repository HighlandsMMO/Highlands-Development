package com.highlands.highlandscore.domain.tables

import org.jetbrains.exposed.dao.id.UUIDTable

object Players: UUIDTable() {
    val name = varchar("name", 256)
}