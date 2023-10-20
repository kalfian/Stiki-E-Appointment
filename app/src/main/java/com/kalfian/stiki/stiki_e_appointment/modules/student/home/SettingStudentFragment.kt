package com.kalfian.stiki.stiki_e_appointment.modules.student.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.google.firebase.messaging.FirebaseMessaging
import com.kalfian.stiki.stiki_e_appointment.R
import com.kalfian.stiki.stiki_e_appointment.databinding.FragmentSettingStudentBinding
import com.kalfian.stiki.stiki_e_appointment.modules.LoginActivity
import com.kalfian.stiki.stiki_e_appointment.models.global.MessageResponse
import com.kalfian.stiki.stiki_e_appointment.modules.AboutActivity
import com.kalfian.stiki.stiki_e_appointment.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Response
import com.kalfian.stiki.stiki_e_appointment.utils.SharedPreferenceUtil
import retrofit2.Callback
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

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
            logout(requireContext(), requireActivity())
        }

        b.btnAbout.setOnClickListener {
            val intent = Intent(requireContext(), AboutActivity::class.java)
            startActivity(intent)
        }

        b.swipeRefreshSettingStudent.setOnRefreshListener {
            b.swipeRefreshSettingStudent.isRefreshing = false
        }

    }

    private fun doLogout(context: Context) {
        invalidateFCMToken()
        SharedPreferenceUtil.clearAll(context)
        val intent = Intent(context, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
    private fun logout(context: Context, activity: Activity) {
        RetrofitClient.callAuth(context).postLogout().enqueue(object : Callback<MessageResponse> {
            override fun onResponse(
                call: Call<MessageResponse>,
                response: Response<MessageResponse>
            ) {
                doLogout(context)
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                MotionToast.createColorToast(activity,"Login Gagal!",
                    t.message.toString(),
                    MotionToastStyle.ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(context, R.font.ubuntu_regular)
                )
                doLogout(context)
            }

        })
    }

    private fun invalidateFCMToken() {
        FirebaseMessaging.getInstance().deleteToken()
    }
}