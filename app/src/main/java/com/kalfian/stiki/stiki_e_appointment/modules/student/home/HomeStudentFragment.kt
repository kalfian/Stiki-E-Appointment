package com.kalfian.stiki.stiki_e_appointment.modules.student.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.kalfian.stiki.stiki_e_appointment.R
import com.kalfian.stiki.stiki_e_appointment.adapters.ListActivityAdapter
import com.kalfian.stiki.stiki_e_appointment.adapters.ListAppointmentAdapter
import com.kalfian.stiki.stiki_e_appointment.databinding.FragmentHomeStudentBinding
import com.kalfian.stiki.stiki_e_appointment.models.Activity
import com.kalfian.stiki.stiki_e_appointment.models.Appointment
import com.kalfian.stiki.stiki_e_appointment.modules.student.DetailActivityActivity


class HomeStudentFragment : Fragment(R.layout.fragment_home_student),
    ListActivityAdapter.AdapterListActivityOnClickListener,
    ListAppointmentAdapter.AdapterAppointmentOnClickListener{

    private lateinit var b: FragmentHomeStudentBinding
    private lateinit var activityAdapter: ListActivityAdapter
    private lateinit var appointmentAdapter: ListAppointmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        b = FragmentHomeStudentBinding.inflate(inflater, container, false)
        return b.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListActivity()
        setupListAppointment()

        getListActivity()
        getListAppointment()

        b.swipeRefreshHomeStudent.setOnRefreshListener {
            if (b.emptyAppointment.visibility == View.GONE) {
                b.emptyAppointment.visibility = View.VISIBLE
                b.recyclerAppointmentStudent.visibility = View.GONE
            } else {
                b.emptyAppointment.visibility = View.GONE
                b.recyclerAppointmentStudent.visibility = View.VISIBLE
            }

            if (b.emptyActivity.visibility == View.GONE) {
                b.emptyActivity.visibility = View.VISIBLE
                b.recyclerActivityStudent.visibility = View.GONE
            } else {
                b.emptyActivity.visibility = View.GONE
                b.recyclerActivityStudent.visibility = View.VISIBLE
            }

            b.swipeRefreshHomeStudent.isRefreshing = false
        }
    }

    private fun setupListActivity() {
        val lm = LinearLayoutManager(activity?.applicationContext, LinearLayoutManager.VERTICAL, false)
        b.recyclerActivityStudent.layoutManager = lm
        activityAdapter = ListActivityAdapter(this)
        b.recyclerActivityStudent.adapter = activityAdapter
    }

    private fun setupListAppointment() {
        val lm = LinearLayoutManager(activity?.applicationContext, LinearLayoutManager.VERTICAL, false)
        b.recyclerAppointmentStudent.layoutManager = lm
        appointmentAdapter = ListAppointmentAdapter(this)
        b.recyclerAppointmentStudent.adapter = appointmentAdapter
    }

    private fun getListActivity() {
        activityAdapter.add(Activity("1", "", "", "", ""))
        b.recyclerActivityStudent.visibility = View.GONE
    }

    private fun getListAppointment() {

        appointmentAdapter.addList(listOf(
            Appointment("1", "", "", "", ""),
            Appointment("1", "", "", "", ""),
            Appointment("1", "", "", "", ""),
            Appointment("1", "", "", "", ""),
            Appointment("1", "", "", "", ""),
            Appointment("1", "", "", "", ""),
            Appointment("1", "", "", "", ""),
        ))
        b.recyclerAppointmentStudent.visibility = View.GONE
    }

    override fun onItemClickListener(data: Appointment) {

    }

    override fun onItemClickListener(data: Activity) {
        val intent = Intent(activity?.applicationContext, DetailActivityActivity::class.java)
        startActivity(intent)
    }
}