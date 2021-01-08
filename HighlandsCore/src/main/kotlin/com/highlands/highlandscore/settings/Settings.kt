package com.highlands.highlandscore.settings

import com.highlands.highlandscore.HighlandsCore


class Settings(highlandsCore: HighlandsCore) {
    private val highlandsCore: HighlandsCore = highlandsCore

    /*
     * Logger
     */
    val consoleLog: Boolean
        get() = highlandsCore.config.getBoolean("console-log")
    val fileLog: Boolean
        get() = highlandsCore.config.getBoolean("file-log")

    /*
     * Mysql
     */
    val useMysql: Boolean
        get() = highlandsCore.config.getBoolean("use-mysql")
    val mysqlHost: String?
        get() = highlandsCore.config.getString("host")
    val mysqlDatabase: String?
        get() = highlandsCore.config.getString("database")
    val mysqlUsername: String?
        get() = highlandsCore.config.getString("username")
    val mysqlPassword: String?
        get() = highlandsCore.config.getString("password")
    val mysqlPort: Int
        get() = highlandsCore.config.getInt("port")

    //TODO: Add settings

}