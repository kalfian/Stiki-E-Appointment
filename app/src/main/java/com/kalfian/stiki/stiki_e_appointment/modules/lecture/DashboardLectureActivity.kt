package com.kalfian.stiki.stiki_e_appointment.modules.lecture

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.kalfian.stiki.stiki_e_appointment.R
import com.kalfian.stiki.stiki_e_appointment.databinding.ActivityDashboardLectureBinding
import com.kalfian.stiki.stiki_e_appointment.modules.lecture.home.AppointmentLectureFragment
import com.kalfian.stiki.stiki_e_appointment.modules.lecture.home.HomeLectureFragment
import com.kalfian.stiki.stiki_e_appointment.modules.lecture.home.SettingLectureFragment
import com.kalfian.stiki.stiki_e_appointment.modules.student.home.AppointmentStudentFragment
import com.kalfian.stiki.stiki_e_appointment.modules.student.home.HomeStudentFragment
import com.kalfian.stiki.stiki_e_appointment.modules.student.home.SettingStudentFragment

class DashboardLectureActivity : AppCompatActivity() {

    private lateinit var b: ActivityDashboardLectureBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityDashboardLectureBinding.inflate(layoutInflater)
        val v = b.root
        setContentView(v)

        val homeFragment = HomeLectureFragment()
        val appointmentFragment = AppointmentLectureFragment()
        val settingFragment = SettingLectureFragment()

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

    private fun makeCurrentFragment(fragment: Fragment, b: ActivityDashboardLectureBinding) {
        supportFragmentManager.beginTransaction().apply {
            replace(b.contentDashboardLecture.id, fragment)
            commit()
        }
    }
}