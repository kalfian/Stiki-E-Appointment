package com.kalfian.stiki.stiki_e_appointment.modules.student.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kalfian.stiki.stiki_e_appointment.R
import com.kalfian.stiki.stiki_e_appointment.databinding.FragmentSettingStudentBinding
import com.kalfian.stiki.stiki_e_appointment.modules.LoginActivity

class SettingStudentFragment : Fragment(R.layout.fragment_setting_student) {

    private lateinit var b: FragmentSettingStudentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = FragmentSettingStudentBinding.inflate(inflater, container, false)
        return b.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        b.logoutBtn.setOnClickListener {
            val intent = Intent(activity?.applicationContext, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}