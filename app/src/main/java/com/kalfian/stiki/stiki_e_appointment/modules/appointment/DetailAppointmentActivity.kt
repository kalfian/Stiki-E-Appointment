package com.kalfian.stiki.stiki_e_appointment.modules.appointment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.kalfian.stiki.stiki_e_appointment.adapters.ListRadioBottomSheetAdapter
import com.kalfian.stiki.stiki_e_appointment.databinding.ActivityDetailAppointmentBinding
import com.kalfian.stiki.stiki_e_appointment.models.Activity
import com.kalfian.stiki.stiki_e_appointment.models.Appointment
import com.kalfian.stiki.stiki_e_appointment.models.CheckRadio
import com.kalfian.stiki.stiki_e_appointment.models.User
import com.kalfian.stiki.stiki_e_appointment.models.appointmentResponse.GetAppointmentDetailResponse
import com.kalfian.stiki.stiki_e_appointment.models.global.MessageResponse
import com.kalfian.stiki.stiki_e_appointment.models.requests.UpdateStatusAppointmentRequest
import com.kalfian.stiki.stiki_e_appointment.modules.chat.ChatActivity
import com.kalfian.stiki.stiki_e_appointment.utils.Alert
import com.kalfian.stiki.stiki_e_appointment.utils.AppointmentStatus
import com.kalfian.stiki.stiki_e_appointment.utils.BottomSheetRequest
import com.kalfian.stiki.stiki_e_appointment.utils.Constant
import com.kalfian.stiki.stiki_e_appointment.utils.Helper
import com.kalfian.stiki.stiki_e_appointment.utils.OverlayLoader
import com.kalfian.stiki.stiki_e_appointment.utils.RetrofitClient
import com.kalfian.stiki.stiki_e_appointment.utils.SharedPreferenceUtil
import com.kalfian.stiki.stiki_e_appointment.utils.bottomSheet
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailAppointmentActivity : AppCompatActivity() {


    private lateinit var b: ActivityDetailAppointmentBinding
    private lateinit var overlayLoader: OverlayLoader
    private lateinit var bottomSheet: BottomSheetDialog

    private var isLecture: Boolean = false
    var id = 0
    private var appointmentStatus = 200

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
            val listRadioBottomSheetAdapter = ListRadioBottomSheetAdapter()
            var listStatus = listOf<CheckRadio>()

            if(appointmentStatus == Constant.STATUS_APPOINTMENT_PENDING) {
                listStatus = listOf(
                    CheckRadio("Disetujui", false, Constant.STATUS_APPOINTMENT_ACCEPTED),
                    CheckRadio("Ditolak", false, Constant.STATUS_APPOINTMENT_REJECTED),
                    CheckRadio("Dibatalkan", false, Constant.STATUS_APPOINTMENT_CANCELED)
                )
            }

            if(appointmentStatus == Constant.STATUS_APPOINTMENT_ACCEPTED) {
                listStatus = listOf(
                    CheckRadio("Selesai", false, Constant.STATUS_APPOINTMENT_DONE)
                )
            }

            listRadioBottomSheetAdapter.addList(listStatus)

            val request = BottomSheetRequest(
                ctx = this,
                title = "Kelola Bimbingan",
                okTitle = "Ubah Status",
                disableOkButton = false,
                btnOkOnClick = {

                    val checked = listRadioBottomSheetAdapter.getChecked()
                    val payload = UpdateStatusAppointmentRequest(checked.id)
                    overlayLoader.show()

                    RetrofitClient.callAuth(applicationContext).putLectureAppointment(id, payload).enqueue(object :
                        Callback<MessageResponse> {
                        override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {
                            if (response.isSuccessful) {
                                Alert.showSuccess(
                                    this@DetailAppointmentActivity,
                                    "Berhasil mengubah status Bimbingan!", "ok"
                                )
                                appointmentStatus = payload.status
                                b.appointmentStatus.text = AppointmentStatus().getStatusText(payload.status)
                                b.appointmentStatus.chipBackgroundColor = AppointmentStatus().getStatusColor(payload.status, applicationContext)
                                bottomSheet.dismiss()
                                overlayLoader.hide()
                            } else {
                                Alert.showError(
                                    this@DetailAppointmentActivity,
                                    "Gagal mengubah status Bimbingan!", "coba lagi"
                                )
                                bottomSheet.dismiss()
                                overlayLoader.hide()
                            }
                        }

                        override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                            Alert.showError(
                                this@DetailAppointmentActivity,
                                "Gagal mengubah status Bimbingan!", "coba lagi"
                            )
                            bottomSheet.dismiss()
                            overlayLoader.hide()
                        }
                    })
                },
                recyclerViewAdapterCheck = listRadioBottomSheetAdapter
            )

            bottomSheet = bottomSheet(request)
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
            override fun onResponse(call: Call<GetAppointmentDetailResponse>, response: retrofit2.Response<GetAppointmentDetailResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    if (data == null) {
                        Alert.showError(
                            this@DetailAppointmentActivity,
                            "Gagal mendapatkan detail Bimbingan!", "coba lagi"
                        )
                        overlayLoader.hide()
                        finish()
                        return
                    }

                    overlayLoader.hide()
                    attachData(data.appointment, data.activity, data.participants.student, data.participants.lecture, data.participants.lecture2)
                } else {
                    overlayLoader.hide()
                    finish()
                }
            }

            override fun onFailure(call: Call<GetAppointmentDetailResponse>, t: Throwable) {
                overlayLoader.hide()
                finish()
            }
        })
    }

    private fun setupPageLecture() {
        RetrofitClient.callAuth(applicationContext).getLectureAppointmentDetail(id).enqueue(object : retrofit2.Callback<GetAppointmentDetailResponse> {
            override fun onResponse(call: Call<GetAppointmentDetailResponse>, response: retrofit2.Response<GetAppointmentDetailResponse>) {
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

            override fun onFailure(call: Call<GetAppointmentDetailResponse>, t: Throwable) {
                overlayLoader.hide()
                finish()
            }
        })
    }

    private fun attachData(appointment: Appointment, activity: Activity, student: User, lecture: User, lecture2: User? = null) {
        b.nav.headerTitle.text = appointment.title

        appointmentStatus = appointment.status

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
            .error(com.kalfian.stiki.stiki_e_appointment.R.drawable.no_image_stiki)
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