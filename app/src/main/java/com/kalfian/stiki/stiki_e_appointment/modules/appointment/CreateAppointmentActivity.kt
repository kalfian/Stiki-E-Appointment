package com.kalfian.stiki.stiki_e_appointment.modules.appointment

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.kalfian.stiki.stiki_e_appointment.databinding.ActivityCreateAppointmentStudentBinding

class CreateAppointmentActivity : AppCompatActivity() {

    private lateinit var b: ActivityCreateAppointmentStudentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityCreateAppointmentStudentBinding.inflate(layoutInflater)
        val v = b.root
        setContentView(v)

        b.nav.headerTitle.text = "Tambah Bimbingan"
        b.nav.backButton.setOnClickListener {
            finish()
        }

        val items = arrayOf("PT Taspen", "PT Jalanin Aja Dulu")
        val adapter = ArrayAdapter(this, androidx.transition.R.layout.support_simple_spinner_dropdown_item, items)
        b.spinnerKegiatan.adapter = adapter
    }
}