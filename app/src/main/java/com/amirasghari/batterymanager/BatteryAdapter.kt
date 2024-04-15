package com.amirasghari.batterymanager

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.amirasghari.batterymanager.databinding.BatteryUsageRowBinding

class BatteryAdapter(private val battery: Map<String, Int>, val context: Context) :
    RecyclerView.Adapter<BatteryAdapter.BatteryViewHolder>() {

    inner class BatteryViewHolder(val binding: BatteryUsageRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: BatteryData) {
            binding.appNameTxt.text = getAppName(data.appName)
            binding.appUsagePercentTxt.text = if(data.batteryUsage.toString().equals("0"))"1" else data.batteryUsage.toString()
            binding.appIconImg.setImageDrawable(getAppIcon(data.appName))
            binding.myProgressBar.progress = data.batteryUsage
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BatteryViewHolder {
        val binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.battery_usage_row,
            parent,
            false
        ) as BatteryUsageRowBinding
        return BatteryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BatteryViewHolder, position: Int) {
        val list = battery.toList()
        val data = BatteryData(list[position].first, list[position].second)
        holder.bind(data)
    }

    override fun getItemCount(): Int = battery.size

    fun getAppName(packageName: String): String {
        val pm = context.applicationContext.packageManager
        val appInfo: ApplicationInfo? = try {
            pm.getApplicationInfo(packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            Log.i("AppNamee" , e.message.toString())
            null
        }

        return (if (appInfo != null) pm.getApplicationLabel(appInfo).toString() else "unknown")

    }

    fun getAppIcon(packageName: String):Drawable?{
        var icon :Drawable? = null
        try {
            icon =  context.packageManager.getApplicationIcon(packageName)
        }catch (e:PackageManager.NameNotFoundException){
            e.printStackTrace()
        }
        return icon
    }
}