package com.kalfian.stiki.stiki_e_appointment.modules

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kalfian.stiki.stiki_e_appointment.BuildConfig
import com.kalfian.stiki.stiki_e_appointment.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    private lateinit var b: ActivityAboutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityAboutBinding.inflate(layoutInflater)
        val v = b.root
        setContentView(v)

        b.nav.headerTitle.text = "Tentang Applikasi"

        b.nav.backButton.setOnClickListener {
            finish()
        }

        b.versionInfo.text = "Versi " + BuildConfig.VERSION_NAME

    }
}