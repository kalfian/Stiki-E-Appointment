package com.kalfian.stiki.stiki_e_appointment.modules.lecture.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.kalfian.stiki.stiki_e_appointment.R
import com.kalfian.stiki.stiki_e_appointment.adapters.ListAppointmentAdapter
import com.kalfian.stiki.stiki_e_appointment.databinding.FragmentAppointmentLectureBinding
import com.kalfian.stiki.stiki_e_appointment.models.Appointment
import com.kalfian.stiki.stiki_e_appointment.models.appointmentResponse.GetAppointmentsResponse
import com.kalfian.stiki.stiki_e_appointment.modules.appointment.DetailAppointmentActivity
import com.kalfian.stiki.stiki_e_appointment.utils.Constant
import com.kalfian.stiki.stiki_e_appointment.utils.OverlayLoader
import com.kalfian.stiki.stiki_e_appointment.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppointmentLectureFragment : Fragment(R.layout.fragment_appointment_lecture),
    ListAppointmentAdapter.AdapterAppointmentOnClickListener{

    private lateinit var b: FragmentAppointmentLectureBinding
    private lateinit var appointmentAdapter: ListAppointmentAdapter
    private lateinit var overlayLoader: OverlayLoader
    private var page = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        b = FragmentAppointmentLectureBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        overlayLoader = OverlayLoader(requireContext())

        setupListAppointment()
        getListAppointment()

        b.swipeRefreshHome.setOnRefreshListener {
            getListAppointment()

            b.swipeRefreshHome.isRefreshing = false
        }

    }

    private fun setupListAppointment() {
        val lm = LinearLayoutManager(activity?.applicationContext, LinearLayoutManager.VERTICAL, false)
        b.recyclerAppointment.layoutManager = lm
        appointmentAdapter = ListAppointmentAdapter(this, true, true)
        b.recyclerAppointment.adapter = appointmentAdapter
    }

    private fun getListAppointment() {
        appointmentAdapter.clear()
        overlayLoader.show()
        RetrofitClient.callAuth(requireContext()).getLectureAppointments(page = page, loadStudent = true)
            .enqueue(object : Callback<GetAppointmentsResponse> {
                override fun onResponse(
                    call: Call<GetAppointmentsResponse>,
                    response: Response<GetAppointmentsResponse>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()?.data
                        if (data != null) {
                            appointmentAdapter.addList(data)
                        }
                    }

                    if (appointmentAdapter.itemCount > 0) {
                        hideEmptyAppointment()
                    } else {
                        showEmptyAppointment()
                    }
                    overlayLoader.hide()
                }

                override fun onFailure(call: Call<GetAppointmentsResponse>, t: Throwable) {
                    showEmptyAppointment()
                    overlayLoader.hide()
                }
            })
    }

    private fun showEmptyAppointment() {
        b.recyclerAppointment.visibility = View.GONE
        b.emptyAppointment.visibility = View.VISIBLE
    }

    private fun hideEmptyAppointment() {
        b.recyclerAppointment.visibility = View.VISIBLE
        b.emptyAppointment.visibility = View.GONE
    }

    override fun onItemClickListener(data: Appointment) {
        val intent = Intent(activity?.applicationContext, DetailAppointmentActivity::class.java)
        intent.putExtra(Constant.DETAIL_APPOINTMENT_ID, data.id)
        startActivity(intent)
    }

}