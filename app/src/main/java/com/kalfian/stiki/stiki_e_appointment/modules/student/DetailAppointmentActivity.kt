package com.kalfian.stiki.stiki_e_appointment.modules.student

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kalfian.stiki.stiki_e_appointment.databinding.ActivityDetailAppointmentBinding

class DetailAppointmentActivity : AppCompatActivity() {


    private lateinit var b: ActivityDetailAppointmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityDetailAppointmentBinding.inflate(layoutInflater)
        val v = b.root
        setContentView(v)

        b.nav.headerTitle.text = "Bimbingan 1"
        b.nav.backButton.setOnClickListener {
            finish()
        }
    }
}