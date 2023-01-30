package com.kalfian.stiki.stiki_e_appointment.modules.lecture

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.kalfian.stiki.stiki_e_appointment.databinding.ActivityDashboardLectureBinding

class DashboardLectureActivity : AppCompatActivity() {

    private lateinit var b: ActivityDashboardLectureBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityDashboardLectureBinding.inflate(layoutInflater)
        val v = b.root
        setContentView(v)

        b.bottomNav.setOnItemSelectedListener {
            when(it.itemId) {
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