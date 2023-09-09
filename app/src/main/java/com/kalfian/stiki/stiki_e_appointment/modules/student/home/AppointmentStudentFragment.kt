package com.kalfian.stiki.stiki_e_appointment.modules.student.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.kalfian.stiki.stiki_e_appointment.R
import com.kalfian.stiki.stiki_e_appointment.adapters.ListAppointmentAdapter
import com.kalfian.stiki.stiki_e_appointment.databinding.FragmentAppointmentStudentBinding
import com.kalfian.stiki.stiki_e_appointment.models.Appointment
import com.kalfian.stiki.stiki_e_appointment.modules.appointment.CreateAppointmentActivity
import com.kalfian.stiki.stiki_e_appointment.modules.appointment.DetailAppointmentActivity

class AppointmentStudentFragment : Fragment(R.layout.fragment_appointment_student),
    ListAppointmentAdapter.AdapterAppointmentOnClickListener{

    private lateinit var b: FragmentAppointmentStudentBinding
    private lateinit var appointmentAdapter: ListAppointmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        b = FragmentAppointmentStudentBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListAppointment()
        getListAppointment()

        b.swipeRefreshHomeStudent.setOnRefreshListener {
            if (b.emptyAppointment.visibility == View.GONE) {
                b.emptyAppointment.visibility = View.VISIBLE
                b.recyclerAppointmentStudent.visibility = View.GONE
            } else {
                b.emptyAppointment.visibility = View.GONE
                b.recyclerAppointmentStudent.visibility = View.VISIBLE
            }

            b.swipeRefreshHomeStudent.isRefreshing = false
        }

        b.createAppointment.setOnClickListener {
            val intent = Intent(activity?.applicationContext, CreateAppointmentActivity::class.java)
            startActivity(intent)
        }

    }

    private fun setupListAppointment() {
        val lm = LinearLayoutManager(activity?.applicationContext, LinearLayoutManager.VERTICAL, false)
        b.recyclerAppointmentStudent.layoutManager = lm
        appointmentAdapter = ListAppointmentAdapter(this, true, true, false)
        b.recyclerAppointmentStudent.adapter = appointmentAdapter
    }

    private fun getListAppointment() {
        appointmentAdapter.clear()
        appointmentAdapter.addList(listOf(
        ))
        b.recyclerAppointmentStudent.visibility = View.GONE
    }

    override fun onItemClickListener(data: Appointment) {
        val intent = Intent(activity?.applicationContext, DetailAppointmentActivity::class.java)
        startActivity(intent)
    }

}