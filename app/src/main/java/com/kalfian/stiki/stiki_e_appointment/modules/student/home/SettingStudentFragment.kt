package com.kalfian.stiki.stiki_e_appointment.modules.student.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kalfian.stiki.stiki_e_appointment.R
import com.kalfian.stiki.stiki_e_appointment.databinding.FragmentSettingStudentBinding

class SettingStudentFragment : Fragment(R.layout.fragment_setting_student) {

    private lateinit var b: FragmentSettingStudentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = FragmentSettingStudentBinding.inflate(inflater, container, false)
        return b.root

    }
}