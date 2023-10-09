package com.kalfian.stiki.stiki_e_appointment.modules

import android.Manifest
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
import pub.devrel.easypermissions.EasyPermissions

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

//            Check notification permission using easy permission
            if (!EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_NOTIFICATION_POLICY)) {
                // Ask for one permission
                EasyPermissions.requestPermissions(
                    this,
                    "Aplikasi ini membutuhkan akses notifikasi untuk dapat berjalan dengan baik.",
                    123,
                    Manifest.permission.ACCESS_NOTIFICATION_POLICY)

//                If permission not granted, then restart app
                val intent = Intent(this, SplashscreenActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)

                return@postDelayed
            }


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