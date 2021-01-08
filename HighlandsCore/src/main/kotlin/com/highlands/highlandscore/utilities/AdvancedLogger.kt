package com.highlands.highlandscore.utilities

import com.highlands.highlandscore.HighlandsCore
import org.bukkit.Bukkit
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.Level

class AdvancedLogger(highlandsCore: HighlandsCore) {
    //HighlandsCore
    private val highlandsCore: HighlandsCore = highlandsCore

    //Logs
    var log_folder: File? = null
    var log_file: File? = null
    private fun setupLogFiles() {
        //Setup Log Files
        val date = Date()
        val dateFormat = SimpleDateFormat("dd_MM_yyyy_HH_mm_ss")
        val time: String = dateFormat.format(date)
        log_folder = File(highlandsCore.getDataFolder().toPath().toString() + "/Logs")
        log_file = File(log_folder!!.toPath().toString() + "/log_" + time + ".txt")
    }

    fun Log(level: Level, plugin: String, message: String?) {
        //Log to Console if Wanted by config
        if (highlandsCore.settings?.consoleLog!!) {
            Bukkit.getLogger().log(level, "[$plugin] $message")
        }

        //Log to File if Wanted by config
        if (highlandsCore.settings?.fileLog!!) {
            try {
                if (!log_folder!!.exists()) {
                    log_folder!!.mkdir()
                    Bukkit.getConsoleSender().sendMessage(log_folder.toString())
                }
                if (!log_file!!.exists()) {
                    log_file!!.createNewFile()
                    return
                }
                val fw = FileWriter(log_file, true)
                val pw = PrintWriter(fw)
                val date = Date()
                val dateFormat = SimpleDateFormat("dd.MM.yyyy - HH:mm:ss")
                val time: String = dateFormat.format(date)
                pw.println("$time : [$level] [$plugin] $message")
                pw.flush()
                pw.close()
            } catch (e: Exception) {
                e.printStackTrace()
                Log(Level.WARNING, "HighlandsCore", "Log file error: $e")
            }
        }
    }

    init {
        setupLogFiles()
    }
}