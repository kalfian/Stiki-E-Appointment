package com.kalfian.stiki.stiki_e_appointment.modules.lecture.home

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
import com.kalfian.stiki.stiki_e_appointment.databinding.FragmentHomeLectureBinding
import com.kalfian.stiki.stiki_e_appointment.models.Activity
import com.kalfian.stiki.stiki_e_appointment.models.Appointment
import com.kalfian.stiki.stiki_e_appointment.models.Participant
import com.kalfian.stiki.stiki_e_appointment.modules.activity.DetailActivityActivity
import com.kalfian.stiki.stiki_e_appointment.modules.appointment.DetailAppointmentActivity
import com.kalfian.stiki.stiki_e_appointment.utils.Constant
import com.kalfian.stiki.stiki_e_appointment.utils.SharedPreferenceUtil


class HomeLectureFragment : Fragment(R.layout.fragment_home_lecture),
    ListActivityAdapter.AdapterListActivityOnClickListener,
    ListAppointmentAdapter.AdapterAppointmentOnClickListener{

    private lateinit var b: FragmentHomeLectureBinding
    private lateinit var activityAdapter: ListActivityAdapter
    private lateinit var appointmentAdapter: ListAppointmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        b = FragmentHomeLectureBinding.inflate(inflater, container, false)
        return b.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListActivity()
        setupListAppointment()

        getListActivity()
        getListAppointment()

        b.swipeRefreshHome.setOnRefreshListener {
            if (b.emptyAppointment.visibility == View.GONE) {
                b.emptyAppointment.visibility = View.VISIBLE
                b.recyclerAppointment.visibility = View.GONE
            } else {
                b.emptyAppointment.visibility = View.GONE
                b.recyclerAppointment.visibility = View.VISIBLE
            }

            if (b.emptyActivity.visibility == View.GONE) {
                b.emptyActivity.visibility = View.VISIBLE
                b.recyclerActivity.visibility = View.GONE
            } else {
                b.emptyActivity.visibility = View.GONE
                b.recyclerActivity.visibility = View.VISIBLE
            }

            b.swipeRefreshHome.isRefreshing = false
        }
    }

    private fun setupListActivity() {
        val lm = LinearLayoutManager(activity?.applicationContext, LinearLayoutManager.VERTICAL, false)
        b.recyclerActivity.layoutManager = lm
        activityAdapter = ListActivityAdapter(this)
        b.recyclerActivity.adapter = activityAdapter
    }

    private fun setupListAppointment() {
        val lm = LinearLayoutManager(activity?.applicationContext, LinearLayoutManager.VERTICAL, false)
        b.recyclerAppointment.layoutManager = lm
        appointmentAdapter = ListAppointmentAdapter(this, false, false)
        b.recyclerAppointment.adapter = appointmentAdapter
    }

    private fun getListActivity() {
        appointmentAdapter.clear()

        b.recyclerActivity.visibility = View.GONE
    }

    private fun getListAppointment() {
        appointmentAdapter.clear()
        b.recyclerAppointment.visibility = View.GONE
    }

    override fun onItemClickListener(data: Appointment) {
        val intent = Intent(activity?.applicationContext, DetailAppointmentActivity::class.java)
        intent.putExtra(Constant.IS_LECTURE, true)
        startActivity(intent)
    }

    override fun onItemClickListener(data: Activity) {
        val intent = Intent(activity?.applicationContext, DetailActivityActivity::class.java)
        intent.putExtra(Constant.IS_LECTURE, true)
        startActivity(intent)
    }
}