package com.highlands.highlandscore.api

import com.highlands.highlandscore.HighlandsCore
import org.bukkit.plugin.ServicePriority

class HighlandsCoreApi(highlandsCore: HighlandsCore) {
    //EtherealCore
    private val highlandsCore: HighlandsCore
    private fun registerService() {
        highlandsCore.server.servicesManager.register(HighlandsCoreApi::class.java, this, highlandsCore, ServicePriority.High)
    }

    init {
        this.highlandsCore = highlandsCore

        //Registers the API Service to be used in other plugins
        registerService()
    }
}