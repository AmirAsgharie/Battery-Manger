package com.amirasghari.batterymanager

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.amirasghari.batterymanager.databinding.ActivitySplashBinding
import java.util.*
import kotlin.concurrent.timerTask

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        Timer().schedule(timerTask {
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, 5000)

        Timer().schedule(timerTask {
            runOnUiThread(timerTask {
                binding.helpTxt.text = "Welcome To Battery Manager"
            })

        }, 1000)

        Timer().schedule(timerTask {
            runOnUiThread(timerTask {
                binding.helpTxt.text = "Created By AmirAsghari"
            })

        }, 2000)

        Timer().schedule(timerTask {
            runOnUiThread(timerTask {
                binding.helpTxt.text = "Let's Go"
            })

        }, 4000)
    }
}