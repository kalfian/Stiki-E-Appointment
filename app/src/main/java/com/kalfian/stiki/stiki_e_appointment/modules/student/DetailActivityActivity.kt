package com.kalfian.stiki.stiki_e_appointment.modules.student

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kalfian.stiki.stiki_e_appointment.databinding.ActivityDetailActivityBinding

class DetailActivityActivity : AppCompatActivity() {

    private lateinit var b: ActivityDetailActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityDetailActivityBinding.inflate(layoutInflater)
        val v = b.root
        setContentView(v)
    }
}