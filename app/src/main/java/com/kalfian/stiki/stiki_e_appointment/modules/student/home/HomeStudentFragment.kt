package com.kalfian.stiki.stiki_e_appointment.modules.student.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.kalfian.stiki.stiki_e_appointment.R
import com.kalfian.stiki.stiki_e_appointment.adapters.ListActivityAdapter
import com.kalfian.stiki.stiki_e_appointment.adapters.ListAppointmentAdapter
import com.kalfian.stiki.stiki_e_appointment.databinding.FragmentHomeStudentBinding
import com.kalfian.stiki.stiki_e_appointment.models.Activity
import com.kalfian.stiki.stiki_e_appointment.models.Appointment
import com.kalfian.stiki.stiki_e_appointment.models.activity_response.GetActivityResponse
import com.kalfian.stiki.stiki_e_appointment.modules.activity.DetailActivityActivity
import com.kalfian.stiki.stiki_e_appointment.modules.appointment.DetailAppointmentActivity
import com.kalfian.stiki.stiki_e_appointment.utils.Constant
import com.kalfian.stiki.stiki_e_appointment.utils.RetrofitClient
import com.kalfian.stiki.stiki_e_appointment.utils.SharedPreferenceUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import kotlinx.coroutines.async


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

        attachIdentity()

        setupListActivity()
        setupListAppointment()

//        Async get data
        getListActivity()
        getListAppointment()

        b.swipeRefreshHomeStudent.setOnRefreshListener {
            getListActivity()
            getListAppointment()


            b.swipeRefreshHomeStudent.isRefreshing = false
        }
    }

    private fun attachIdentity() {

        val name = SharedPreferenceUtil.retrieve(requireContext(), Constant.SHARED_NAME, "-")
        val identity = SharedPreferenceUtil.retrieve(requireContext(), Constant.SHARED_IDENTITY, "-")

        b.studentName.text = name
        b.studentIdentity.text = identity
    }
    private fun setupListActivity() {
        val lm = LinearLayoutManager(activity?.applicationContext, LinearLayoutManager.HORIZONTAL, false)
        b.recyclerActivityStudent.layoutManager = lm
        activityAdapter = ListActivityAdapter(this, false)
        b.recyclerActivityStudent.adapter = activityAdapter
    }

    private fun setupListAppointment() {
        val lm = LinearLayoutManager(activity?.applicationContext, LinearLayoutManager.VERTICAL, false)
        b.recyclerAppointmentStudent.layoutManager = lm
        appointmentAdapter = ListAppointmentAdapter(this, false, false, false)
        b.recyclerAppointmentStudent.adapter = appointmentAdapter
    }

    private fun getListActivity() {
        appointmentAdapter.clear()

        RetrofitClient.callAuth(requireContext()).getStudentActivity().enqueue(object : Callback<GetActivityResponse> {
            override fun onResponse(
                call: Call<GetActivityResponse>,
                response: Response<GetActivityResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    if (data != null) {
                        activityAdapter.clear()
                        activityAdapter.addList(data)

                        if (activityAdapter.itemCount > 0) {
                            showActivity()
                        } else {
                            hideActivity()
                        }
                        return
                    }

                    hideActivity()
                }
            }

            override fun onFailure(call: Call<GetActivityResponse>, t: Throwable) {
                hideActivity()
                MotionToast.createColorToast(requireActivity(),"Gagal mendapatkan kegiatan!",
                    "Periksa koneksi internet anda dan coba lagi",
                    MotionToastStyle.ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(requireContext(), R.font.ubuntu_regular)
                )
            }

        })
    }

    private fun hideActivity() {
        b.emptyActivity.visibility = View.VISIBLE
        b.recyclerActivityStudent.visibility = View.GONE
    }

    private fun showActivity() {
        b.emptyActivity.visibility = View.GONE
        b.recyclerActivityStudent.visibility = View.VISIBLE
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

    override fun onItemClickListener(data: Activity) {
        val intent = Intent(activity?.applicationContext, DetailActivityActivity::class.java)
        intent.putExtra(Constant.DETAIL_ACTIVITY_ID, data.id)
        startActivity(intent)
    }
}