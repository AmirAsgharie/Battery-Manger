package com.amirasghari.batterymanager

import android.content.*
import android.os.BatteryManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.amirasghari.batterymanager.databinding.FragmentMainBinding


class MainFragment : Fragment() {


    lateinit var binding: FragmentMainBinding
    lateinit var shared :SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_main, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shared = requireActivity().getSharedPreferences("battery" , 0)
        val slide_up = AnimationUtils.loadAnimation(requireContext() , R.anim.slide_up)
        binding.batteryInfoCons.startAnimation(slide_up)
        val fade_in = AnimationUtils.loadAnimation(requireContext() , R.anim.fade_in)
        binding.circularProgressBar.startAnimation(fade_in)
        binding.progressBarPercentTxt.startAnimation(fade_in)

        binding.circularProgressBar.setOnClickListener{
            NavHostFragment.findNavController(this).navigate(R.id.action_mainFragment_to_batteryUsageFragment)
        }





        requireActivity().registerReceiver(batteryInfo, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

    }

    private var batteryInfo: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val voltage =(intent?.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0)?.div(1000)).toString()
            binding.showBatteryVoltageTxt.text = voltage

            val technology = intent?.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY)
            binding.showBatteryTechnologyTxt.text = technology

            val temp = (intent?.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)?.div(10)).toString()
            binding.showBatteryTemperatureTxt.text = temp

            val plugChecker: Int? = intent?.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0)
            binding.showBatteryPlugTxt.text = if (plugChecker == 0) "No" else " Charging"

            val percent = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, 100)
            progressBar(percent!!)

            val edit = shared.edit()
            edit.putString("Voltage" ,voltage )
            edit.putString("Technology" ,technology )
            edit.putString("Temp" ,technology )
            edit.putInt("Percent",percent)
            edit.putInt("Plug" , plugChecker!! )
            edit.apply()

        }
    }

    private fun progressBar(percent: Int) {
        val progressBar = binding.circularProgressBar
        progressBar.setProgressWithAnimation(percent.toFloat(), 2000)
        binding.progressBarPercentTxt.text = buildString {
            append(" ")
            append(percent)
            append("%")
        }
    }

    override fun onResume() {
        super.onResume()
        loadData()

    }

    private fun loadData(){
        val voltage = shared.getString("Voltage" , "3")
        val technology = shared.getString("Technology" , "Li-ion")
        val temp = shared.getString("Temp" , "34")
        val plug = if (shared.getInt("Plug" , 0)==0) "No" else "Charging"
        val percent = shared.getInt("Percent" , 0)

        binding.showBatteryVoltageTxt.text = voltage
        binding.showBatteryTechnologyTxt.text = technology
        binding.showBatteryTemperatureTxt.text = temp
        binding.showBatteryPlugTxt.text = plug
        progressBar(percent)
    }


}