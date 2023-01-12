package com.kalfian.stiki.stiki_e_appointment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import com.kalfian.stiki.stiki_e_appointment.databinding.ActivitySplashscreenBinding

class SplashscreenActivity : AppCompatActivity() {
    private val SPLASH_TIME: Long = 2000
    private lateinit var b: ActivitySplashscreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivitySplashscreenBinding.inflate(layoutInflater)
        val v = b.root
        setContentView(v)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        Handler(Looper.getMainLooper()).postDelayed({
//            if(auth.currentUser != null) {
//                val intent = Intent(this, DashboardActivity::class.java)
//                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                startActivity(intent)
//                return@postDelayed
//            }

            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }, SPLASH_TIME)
    }
}