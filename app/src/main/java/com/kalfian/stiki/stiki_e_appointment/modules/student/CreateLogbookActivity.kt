package com.kalfian.stiki.stiki_e_appointment.modules.student

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import com.kalfian.stiki.stiki_e_appointment.R
import com.kalfian.stiki.stiki_e_appointment.databinding.ActivityCreateLogbookBinding

class CreateLogbookActivity : AppCompatActivity() {

    private lateinit var b: ActivityCreateLogbookBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        b = ActivityCreateLogbookBinding.inflate(layoutInflater)
        val v = b.root
        setContentView(v)

        b.nav.headerTitle.text = "Tambah Logbook"
        b.nav.backButton.setOnClickListener {
            finish()
        }

        b.btnLogin.setOnClickListener {

        }
    }
}