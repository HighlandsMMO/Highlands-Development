package com.highlands.highlandscore.infrastructure

import com.highlands.highlandscore.HighlandsCore
import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.SQLException
import java.util.logging.Level

class HighlandsCoreDB {
    //HighlandsCore
    private var highlandsCore: HighlandsCore? = null

    // getConnection
    //Mysql
    var connection: Connection? = null
    var host: String? = null
    var database: String? = null
    var username: String? = null
    var password: String? = null
    var port = 0

    //SQLite
    var sqliteFile = File("plugins/HighlandsCore/HighlandsCore.db")
    fun setupMysql(highlandsCore: HighlandsCore) {
        this.highlandsCore = highlandsCore

        //Load the Mysql Config
        host = highlandsCore.settings?.mysqlHost
        database = highlandsCore.settings?.mysqlDatabase
        username = highlandsCore.settings?.mysqlUsername
        password = highlandsCore.settings?.mysqlPassword
        port = highlandsCore.settings?.mysqlPort!!
    }

    fun setupSQLite(highlandsCore: HighlandsCore?) {
        this.highlandsCore = highlandsCore
    }

    // connect
    @Throws(SQLException::class)
    fun connect() {
        if (!isConnected) {
            try {
                if (highlandsCore?.settings?.useMysql!!) {
                    connection = DriverManager.getConnection("jdbc:mysql://$host:$port/$database", username, password)
                    val ps: PreparedStatement = connection!!.prepareStatement(
                        "CREATE TABLE IF NOT EXISTS " +
                                "PlayerData (Name VARCHAR(100)," +
                                "UUID VARCHAR(100)," +
                                "PRIMARY KEY (Name))"
                    )
                    ps.executeUpdate()
                    highlandsCore!!.advancedLogger?.Log(Level.INFO, "HighlandsCore", "Main MySQL Server Database has been Connected!")
                } else {
                    connection = DriverManager.getConnection("jdbc:sqlite:" + sqliteFile.absolutePath)
                    val ps: PreparedStatement = connection!!.prepareStatement(
                        "CREATE TABLE IF NOT EXISTS " +
                                "PlayerData (Name VARCHAR(100)," +
                                "UUID VARCHAR(100)," +
                                "PRIMARY KEY (Name))"
                    )
                    ps.executeUpdate()
                    highlandsCore!!.advancedLogger?.Log(Level.INFO, "HighlandsCore", "Main SQLite Database has been Connected!")
                }
            } catch (e: SQLException) {
                highlandsCore!!.advancedLogger?.Log(
                    Level.SEVERE,
                    "HighlandsCore",
                    "Couldnt make new PlayerData Table or Connect to the Database: $e"
                )
            }
        }
    }

    // disconnect
    @Throws(SQLException::class)
    fun disconnect() {
        if (isConnected) {
            try {
                connection!!.close()
                highlandsCore!!.advancedLogger?.Log(Level.INFO, "HighlandsCore", "Database has been disconnected!")
            } catch (e: SQLException) {
                highlandsCore!!.advancedLogger?.Log(Level.SEVERE, "HighlandsCore", "Couldnt disconnect from Database: $e")
            }
        }
    }

    // isConnected
    @get:Throws(SQLException::class)
    val isConnected: Boolean
        get() {
            if (connection == null) return false
            if (connection!!.isClosed) {
                highlandsCore!!.advancedLogger?.Log(Level.SEVERE, "HighlandsCore", "Database connection was closed. Reconnecting to Database.")
                return false
            }
            return true
        }
}