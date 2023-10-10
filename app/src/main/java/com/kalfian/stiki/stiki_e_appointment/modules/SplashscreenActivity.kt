package com.kalfian.stiki.stiki_e_appointment.modules

import android.Manifest
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.kalfian.stiki.stiki_e_appointment.databinding.ActivitySplashscreenBinding
import com.kalfian.stiki.stiki_e_appointment.modules.lecture.DashboardLectureActivity
import com.kalfian.stiki.stiki_e_appointment.modules.student.DashboardStudentActivity
import com.kalfian.stiki.stiki_e_appointment.utils.Constant
import com.kalfian.stiki.stiki_e_appointment.utils.SharedPreferenceUtil
import pub.devrel.easypermissions.EasyPermissions

class SplashscreenActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    private lateinit var b: ActivitySplashscreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivitySplashscreenBinding.inflate(layoutInflater)
        val v = b.root
        setContentView(v)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (!EasyPermissions.hasPermissions(this, Manifest.permission.POST_NOTIFICATIONS)) {
                // Ask for one permission
                EasyPermissions.requestPermissions(
                    this,
                    "Aplikasi ini membutuhkan akses notifikasi untuk dapat berjalan dengan baik.",
                    123,
                    Manifest.permission.POST_NOTIFICATIONS)
                return
            }
        }

        processFlow()


    }

    private fun processFlow(delay: Int = 1000 ) {
        Handler(Looper.getMainLooper()).postDelayed({

            val goTo = when(SharedPreferenceUtil.retrieve(this, Constant.SHARED_ROLE, "")) {
                Constant.ROLE_LECTURE -> {
                    Intent(this, DashboardLectureActivity::class.java)
                }
                Constant.ROLE_STUDENT -> {
                    Intent(this, DashboardStudentActivity::class.java)
                }
                else -> {
                    Intent(this, LoginActivity::class.java)
                }
            }

            goTo.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(goTo)
        }, delay.toLong())
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        processFlow(0)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Toast.makeText(this, "Aplikasi ini membutuhkan akses notifikasi untuk dapat berjalan dengan baik.", Toast.LENGTH_LONG).show()
        finish()
    }
}