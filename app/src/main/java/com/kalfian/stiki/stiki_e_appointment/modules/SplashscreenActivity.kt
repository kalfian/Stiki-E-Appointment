package com.kalfian.stiki.stiki_e_appointment.modules

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import com.kalfian.stiki.stiki_e_appointment.databinding.ActivitySplashscreenBinding
import com.kalfian.stiki.stiki_e_appointment.modules.lecture.DashboardLectureActivity
import com.kalfian.stiki.stiki_e_appointment.modules.student.DashboardStudentActivity
import com.kalfian.stiki.stiki_e_appointment.utils.Constant
import com.kalfian.stiki.stiki_e_appointment.utils.SharedPreferenceUtil

class SplashscreenActivity : AppCompatActivity() {
    private lateinit var b: ActivitySplashscreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivitySplashscreenBinding.inflate(layoutInflater)
        val v = b.root
        setContentView(v)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        Handler(Looper.getMainLooper()).postDelayed({
            val role = SharedPreferenceUtil.retrieve(this, Constant.SHARED_ROLE, "")

            if (role == Constant.ROLE_LECTURE) {
                val intent = Intent(this, DashboardLectureActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                return@postDelayed
            } else if (role == Constant.ROLE_STUDENT) {
                val intent = Intent(this, DashboardStudentActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                return@postDelayed
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }

        }, 2000)
    }
}