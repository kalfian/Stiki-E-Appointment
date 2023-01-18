package com.kalfian.stiki.stiki_e_appointment.modules

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kalfian.stiki.stiki_e_appointment.databinding.ActivityLoginBinding
import com.kalfian.stiki.stiki_e_appointment.modules.student.DashboardStudentActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var b: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityLoginBinding.inflate(layoutInflater)
        val v = b.root
        setContentView(v)

        b.btnLogin.setOnClickListener {
            val intent = Intent(this, DashboardStudentActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}