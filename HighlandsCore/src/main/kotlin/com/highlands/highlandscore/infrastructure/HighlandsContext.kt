package com.highlands.highlandscore.infrastructure

import com.highlands.highlandscore.core.HighlandsCore
import com.highlands.highlandscore.domain.tables.Players
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File

class HighlandsContext(var highlandsCore: HighlandsCore) {
    lateinit var database: Database

    //SQLite
    private var sqliteFile = File("plugins/HighlandsCore/HighlandsCore.db")

    init {
        var settings = highlandsCore.settings
        database = if (settings?.useMysql!!) Database.connect("jdbc:mysql://${settings?.mysqlHost}:${settings?.mysqlPort}/${settings?.mysqlDatabase}", "com.mysql.jdbc.Driver", settings?.mysqlUsername!!, settings?.mysqlPassword!!)
        else Database.connect("jdbc:sqlite:${sqliteFile.absolutePath}", "org.sqlite.JDBC")

        setupTables()
    }

    private fun setupTables() {
        transaction {
            //Load Tables
            SchemaUtils.createMissingTablesAndColumns(Players)
        }
    }
}