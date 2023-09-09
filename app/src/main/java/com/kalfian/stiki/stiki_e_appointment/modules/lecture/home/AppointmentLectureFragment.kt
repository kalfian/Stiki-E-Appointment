package com.kalfian.stiki.stiki_e_appointment.modules.lecture.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.kalfian.stiki.stiki_e_appointment.R
import com.kalfian.stiki.stiki_e_appointment.adapters.ListAppointmentAdapter
import com.kalfian.stiki.stiki_e_appointment.databinding.FragmentAppointmentLectureBinding
import com.kalfian.stiki.stiki_e_appointment.models.Appointment
import com.kalfian.stiki.stiki_e_appointment.modules.appointment.DetailAppointmentActivity

class AppointmentLectureFragment : Fragment(R.layout.fragment_appointment_lecture),
    ListAppointmentAdapter.AdapterAppointmentOnClickListener{

    private lateinit var b: FragmentAppointmentLectureBinding
    private lateinit var appointmentAdapter: ListAppointmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        b = FragmentAppointmentLectureBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListAppointment()
        getListAppointment()

        b.swipeRefreshHome.setOnRefreshListener {
            if (b.emptyAppointment.visibility == View.GONE) {
                b.emptyAppointment.visibility = View.VISIBLE
                b.recyclerAppointment.visibility = View.GONE
            } else {
                b.emptyAppointment.visibility = View.GONE
                b.recyclerAppointment.visibility = View.VISIBLE
            }

            b.swipeRefreshHome.isRefreshing = false
        }

    }

    private fun setupListAppointment() {
        val lm = LinearLayoutManager(activity?.applicationContext, LinearLayoutManager.VERTICAL, false)
        b.recyclerAppointment.layoutManager = lm
        appointmentAdapter = ListAppointmentAdapter(this, true, true, true)
        b.recyclerAppointment.adapter = appointmentAdapter
    }

    private fun getListAppointment() {
        appointmentAdapter.clear()
        appointmentAdapter.addList(listOf(
        ))
        b.recyclerAppointment.visibility = View.GONE
    }

    override fun onItemClickListener(data: Appointment) {
        val intent = Intent(activity?.applicationContext, DetailAppointmentActivity::class.java)
        startActivity(intent)
    }

}