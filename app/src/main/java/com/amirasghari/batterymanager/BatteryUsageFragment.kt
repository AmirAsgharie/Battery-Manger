package com.amirasghari.batterymanager

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amirasghari.batterymanager.databinding.FragmentBatteryUsageBinding


class BatteryUsageFragment : Fragment() {

    lateinit var binding:FragmentBatteryUsageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_battery_usage , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val usage = BatteryUsage(requireContext()).getUsageStateList()
        var allTimeUse:Long = 0
        for (i in usage){
            allTimeUse+=i.totalTimeInForeground.toInt()
        }
        var data:ArrayList<BatteryData>  = ArrayList()
        for (i in usage){
            if (i.totalTimeInForeground.toInt()!=0){
                val btu = (((i.totalTimeInForeground.toFloat())/allTimeUse.toFloat())*100).toInt()
                val batteryData = BatteryData(i.packageName,btu.toInt())
                data.add(batteryData)
            }
        }
        data.sortWith(compareBy<BatteryData> { it.batteryUsage })
        data.reverse()
        val groupData = data.groupBy {it.appName}.mapValues { entry -> entry.value.sumBy { it.batteryUsage } }



        for (i in groupData){
            Log.i("BatteryData" , "${i.key} : ${i.value}")
        }

        val adapter = BatteryAdapter(groupData , requireContext())
        binding.rec.layoutManager = LinearLayoutManager(requireContext() , RecyclerView.VERTICAL , false)
        binding.rec.adapter = adapter

    }




}