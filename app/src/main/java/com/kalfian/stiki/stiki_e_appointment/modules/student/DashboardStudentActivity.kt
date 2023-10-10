package com.kalfian.stiki.stiki_e_appointment.modules.student

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kalfian.stiki.stiki_e_appointment.R
import com.kalfian.stiki.stiki_e_appointment.databinding.ActivityDashboardStudentBinding
import com.kalfian.stiki.stiki_e_appointment.modules.AboutActivity
import com.kalfian.stiki.stiki_e_appointment.modules.student.home.AppointmentStudentFragment
import com.kalfian.stiki.stiki_e_appointment.modules.student.home.HomeStudentFragment
import com.kalfian.stiki.stiki_e_appointment.modules.student.home.SettingStudentFragment

class DashboardStudentActivity : AppCompatActivity() {

    private lateinit var b: ActivityDashboardStudentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityDashboardStudentBinding.inflate(layoutInflater)
        val v = b.root
        setContentView(v)

        val goToNotification = intent.getBooleanExtra("goToNotification", false)
        if (goToNotification) {
            val intent = Intent(applicationContext, AboutActivity::class.java)
            startActivity(intent)
        }

        val homeFragment = HomeStudentFragment()
        val appointmentFragment = AppointmentStudentFragment()
        val settingFragment = SettingStudentFragment()

        makeCurrentFragment(homeFragment, b)

        b.bottomNav.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.home_bottom_nav -> makeCurrentFragment(homeFragment, b)
                R.id.appointment_bottom_nav -> makeCurrentFragment(appointmentFragment, b)
                R.id.setting_bottom_nav -> makeCurrentFragment(settingFragment, b)
            }
            true
        }

    }

    private fun makeCurrentFragment(fragment: Fragment, b: ActivityDashboardStudentBinding) {
        supportFragmentManager.beginTransaction().apply {
            replace(b.contentDashboardStudent.id, fragment)
            commit()
        }
    }
}