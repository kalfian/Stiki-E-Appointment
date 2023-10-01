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
import com.kalfian.stiki.stiki_e_appointment.models.appointmentResponse.GetAppointmentsResponse
import com.kalfian.stiki.stiki_e_appointment.modules.appointment.CreateAppointmentActivity
import com.kalfian.stiki.stiki_e_appointment.modules.appointment.DetailAppointmentActivity
import com.kalfian.stiki.stiki_e_appointment.utils.Constant
import com.kalfian.stiki.stiki_e_appointment.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppointmentStudentFragment : Fragment(R.layout.fragment_appointment_student),
    ListAppointmentAdapter.AdapterAppointmentOnClickListener{

    private lateinit var b: FragmentAppointmentStudentBinding
    private lateinit var appointmentAdapter: ListAppointmentAdapter
    private var page = 1

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
            getListAppointment()
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
        appointmentAdapter = ListAppointmentAdapter(this, true, true)
        b.recyclerAppointmentStudent.adapter = appointmentAdapter
    }

    private fun getListAppointment() {
        appointmentAdapter.clear()

        RetrofitClient.callAuth(requireContext()).getStudentAppointments(true, null, "created_at", "desc", 10,page)
            .enqueue(object : Callback<GetAppointmentsResponse>  {
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
                }

                override fun onFailure(call: Call<GetAppointmentsResponse>, t: Throwable) {
                    showEmptyAppointment()

                }

            })
    }

    private fun showEmptyAppointment() {
        b.recyclerAppointmentStudent.visibility = View.GONE
        b.emptyAppointment.visibility = View.VISIBLE
    }

    private fun hideEmptyAppointment() {
        b.recyclerAppointmentStudent.visibility = View.VISIBLE
        b.emptyAppointment.visibility = View.GONE
    }

    override fun onItemClickListener(data: Appointment) {
        val intent = Intent(activity?.applicationContext, DetailAppointmentActivity::class.java)
        intent.putExtra(Constant.DETAIL_APPOINTMENT_ID, data.id)
        startActivity(intent)
    }

}