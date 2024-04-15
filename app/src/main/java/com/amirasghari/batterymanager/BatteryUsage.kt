package com.amirasghari.batterymanager

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.provider.Settings
import java.util.*

class BatteryUsage(context: Context) {
    val context = context

    init {
        if (getUsageStateList().isEmpty()){
            val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            context.startActivity(intent)
        }
    }

     fun getUsageStateList(): List<UsageStats> {
        val usm = getUsageStateManager()
        val endTime = System.currentTimeMillis()
        val startTime =(System.currentTimeMillis())-(24*60*60*1000)
        return usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY , startTime , endTime)
    }

    private fun getUsageStateManager(): UsageStatsManager {
        return context.getSystemService("usagestats") as UsageStatsManager
    }

}