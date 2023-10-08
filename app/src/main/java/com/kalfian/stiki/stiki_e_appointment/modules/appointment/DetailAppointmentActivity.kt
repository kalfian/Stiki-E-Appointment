package com.kalfian.stiki.stiki_e_appointment.modules.appointment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.google.android.material.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.kalfian.stiki.stiki_e_appointment.databinding.ActivityDetailAppointmentBinding
import com.kalfian.stiki.stiki_e_appointment.models.Activity
import com.kalfian.stiki.stiki_e_appointment.models.Appointment
import com.kalfian.stiki.stiki_e_appointment.models.User
import com.kalfian.stiki.stiki_e_appointment.models.appointmentResponse.GetAppointmentDetailResponse
import com.kalfian.stiki.stiki_e_appointment.modules.chat.ChatActivity
import com.kalfian.stiki.stiki_e_appointment.utils.Alert
import com.kalfian.stiki.stiki_e_appointment.utils.AppointmentStatus
import com.kalfian.stiki.stiki_e_appointment.utils.Constant
import com.kalfian.stiki.stiki_e_appointment.utils.Helper
import com.kalfian.stiki.stiki_e_appointment.utils.OverlayLoader
import com.kalfian.stiki.stiki_e_appointment.utils.RetrofitClient
import com.kalfian.stiki.stiki_e_appointment.utils.SharedPreferenceUtil

class DetailAppointmentActivity : AppCompatActivity() {


    private lateinit var b: ActivityDetailAppointmentBinding
    private lateinit var overlayLoader: OverlayLoader

    private var isLecture: Boolean = false
    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityDetailAppointmentBinding.inflate(layoutInflater)
        val v = b.root
        setContentView(v)
        overlayLoader = OverlayLoader(this)

        isLecture = Helper.stringToBoolean(SharedPreferenceUtil.retrieve(applicationContext, Constant.SHARED_IS_LECTURE, "false"))
        id = intent.getIntExtra(Constant.DETAIL_APPOINTMENT_ID, id)
        if (id == 0) {
            Alert.showError(
                this@DetailAppointmentActivity,
                "Gagal mendapatkan detail Bimbingan!", "coba lagi"
            )
            finish()
        }

        b.nav.backButton.setOnClickListener {
            finish()
        }

        b.btnChat.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra(Constant.INTENT_CHAT_TITLE, b.nav.headerTitle.text)
            intent.putExtra(Constant.INTENT_APPOINTMENT_ID, id)
            startActivity(intent)
        }

        b.btnChangeStatusLogbook.setOnClickListener {
            val dialog = BottomSheetDialog(this, R.style.Theme_Design_Light_BottomSheetDialog)
            val dialogView = LayoutInflater.from(applicationContext).inflate(
                com.kalfian.stiki.stiki_e_appointment.R.layout.custom_bottom_sheet,
                findViewById<LinearLayout>(com.kalfian.stiki.stiki_e_appointment.R.id.status_bottom_sheet)
            )

            dialogView.findViewById<View>(com.kalfian.stiki.stiki_e_appointment.R.id.bottom_close_btn).setOnClickListener {
                dialog.dismiss()
            }

            dialog.setContentView(dialogView)
            dialog.show()
        }

        setupPage(isLecture)

        b.swipeRefreshDetailAppointment.setOnRefreshListener {
            setupPage(isLecture)
            b.swipeRefreshDetailAppointment.isRefreshing = false
        }

    }

    private fun setupPage(isLecture: Boolean) {
        overlayLoader.show()
        if (isLecture) {
            b.containerParticipant.visibility = View.VISIBLE
            b.btnChangeStatusLogbook.visibility = View.VISIBLE
            setupPageLecture()
        } else {
            b.containerParticipant.visibility = View.GONE
            b.btnChangeStatusLogbook.visibility = View.GONE
            setupPageStudent()
        }
    }

    private fun setupPageStudent() {
        RetrofitClient.callAuth(applicationContext).getStudentAppointmentDetail(id).enqueue(object : retrofit2.Callback<GetAppointmentDetailResponse> {
            override fun onResponse(call: retrofit2.Call<GetAppointmentDetailResponse>, response: retrofit2.Response<GetAppointmentDetailResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    if (data == null) {
                        Alert.showError(
                            this@DetailAppointmentActivity,
                            "Gagal mendapatkan detail Bimbingan!", "coba lagi"
                        )
                        finish()
                        return
                    }

                    attachData(data.appointment, data.activity, data.participants.student, data.participants.lecture, data.participants.lecture2)
                } else {
                    finish()
                }
                overlayLoader.hide()
            }

            override fun onFailure(call: retrofit2.Call<GetAppointmentDetailResponse>, t: Throwable) {
                overlayLoader.hide()
                finish()
            }
        })
    }

    private fun setupPageLecture() {

    }

    private fun attachData(appointment: Appointment, activity: Activity, student: User, lecture: User, lecture2: User? = null) {
        b.nav.headerTitle.text = appointment.title

        Glide.with(applicationContext)
            .load(activity.banner)
            .placeholder(CircularProgressDrawable(applicationContext).apply {
                setColorSchemeColors(
                    ContextCompat.getColor(applicationContext, com.kalfian.stiki.stiki_e_appointment.R.color.dark_blue)
                )
                strokeWidth = 2f
                centerRadius = 10f
                start()
            })
            .error(com.kalfian.stiki.stiki_e_appointment.R.drawable.noimage)
            .into(b.activityBanner)
        b.activityTitle.text = activity.name
        b.activityLocation.text = activity.location
        b.activityDate.text = buildString {
            append(activity.startDate)
            append("-")
            append(activity.endDate)
        }

        b.participantName.text = student.name
        b.participantIdentity.text = student.identity


        b.appointmentTitle.text = appointment.title
        b.appointmentStartDate.text = appointment.startDate
        b.appointmentEndDate.text = appointment.endDate
        b.appointmentLocation.text = appointment.location

        b.appointmentDescription.text = appointment.description

        b.appointmentLecturer1.text = lecture.name
        b.appointmentLecturer2.text = lecture2?.name ?: "-"

        if (lecture2 == null) {
            b.containerLecture2.visibility = View.GONE
        } else {
            b.containerLecture2.visibility = View.VISIBLE
        }

        b.appointmentStatus.text = appointment.statusText
        b.appointmentStatus.chipBackgroundColor = AppointmentStatus().getStatusColor(appointment.status, applicationContext)

    }
}