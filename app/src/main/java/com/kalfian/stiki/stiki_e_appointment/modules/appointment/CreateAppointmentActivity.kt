package com.kalfian.stiki.stiki_e_appointment.modules.appointment

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kalfian.stiki.stiki_e_appointment.adapters.ListCheckLectureAdapter
import com.kalfian.stiki.stiki_e_appointment.databinding.ActivityCreateAppointmentStudentBinding
import com.kalfian.stiki.stiki_e_appointment.models.Activity
import com.kalfian.stiki.stiki_e_appointment.models.CheckLecture
import com.kalfian.stiki.stiki_e_appointment.models.activity_response.GetActivityDetailResponse
import com.kalfian.stiki.stiki_e_appointment.models.activity_response.GetActivityResponse
import com.kalfian.stiki.stiki_e_appointment.models.global.ErrorResponse
import com.kalfian.stiki.stiki_e_appointment.models.global.MessageResponse
import com.kalfian.stiki.stiki_e_appointment.requests.CreateAppointmentRequest
import com.kalfian.stiki.stiki_e_appointment.utils.Alert
import com.kalfian.stiki.stiki_e_appointment.utils.OverlayLoader
import com.kalfian.stiki.stiki_e_appointment.utils.RetrofitClient
import com.kalfian.stiki.stiki_e_appointment.utils.showDatePickerDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar
import java.util.Locale

class CreateAppointmentActivity : AppCompatActivity() {

    private lateinit var b: ActivityCreateAppointmentStudentBinding
    private lateinit var arrayAdapter: ArrayAdapter<String>
    private lateinit var listCheckLectureAdapter: ListCheckLectureAdapter
    private lateinit var overlayLoader: OverlayLoader
    var listActivity: List<Activity> = listOf()
    private val calendar = Calendar.getInstance()

    private var requestActivityId = 0
    private var requestTitle = ""
    private var requestDate = ""
    private var requestDescription = ""
    private var requestLocation = ""
    private var requestLectureIds = arrayListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityCreateAppointmentStudentBinding.inflate(layoutInflater)
        val v = b.root
        setContentView(v)

        overlayLoader = OverlayLoader(this)

        b.nav.headerTitle.text = "Tambah Bimbingan"
        b.nav.backButton.setOnClickListener {
            finish()
        }

        setupListActivity()
        setupListLecture()

        loadListActivity()

        b.swipeRefreshCreateAppointment.setOnRefreshListener {
            loadListActivity()
            b.swipeRefreshCreateAppointment.isRefreshing = false
        }

        b.btnCreateAppointment.setOnClickListener {
            submitInput()
        }

        b.dateInput.showDatePickerDialog(this, callback = { dateShowed, dateDB ->
            b.dateInput.setText(dateShowed)
            requestDate = dateDB
        })
    }
    private  fun setupListActivity() {
        arrayAdapter = ArrayAdapter(this, androidx.transition.R.layout.support_simple_spinner_dropdown_item)
        b.spinnerKegiatan.adapter = arrayAdapter
    }

    private fun setupListLecture() {
        val lm = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        b.recyclerSelectLecture.layoutManager = lm
        listCheckLectureAdapter = ListCheckLectureAdapter()
        b.recyclerSelectLecture.adapter = listCheckLectureAdapter
    }
    private fun loadListActivity() {
        arrayAdapter.clear()
        overlayLoader.show()
        RetrofitClient.callAuth(applicationContext).getStudentActivity().enqueue(object : Callback<GetActivityResponse> {
            override fun onResponse(
                call: Call<GetActivityResponse>,
                response: Response<GetActivityResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    if (data != null) {
                        listActivity = data
                        val listActivityText = listActivity.map { it.name }
                        arrayAdapter.addAll(listActivityText)
                    }
                }
                overlayLoader.hide()
            }

            override fun onFailure(call: Call<GetActivityResponse>, t: Throwable) {
                overlayLoader.hide()
                Alert.showError(
                    this@CreateAppointmentActivity,
                    "Gagal mendapatkan data kegiatan!",
                    "Periksa koneksi internet anda dan coba lagi"
                )
            }
        })

        b.spinnerKegiatan.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {
                listCheckLectureAdapter.clear()
            }

            override fun onItemSelected(
                parent: android.widget.AdapterView<*>?,
                view: android.view.View?,
                position: Int,
                id: Long
            ) {
                overlayLoader.show()
                val activity = listActivity[position]
                requestActivityId = activity.id
                listCheckLectureAdapter.clear()
                overlayLoader.hide()

                RetrofitClient.callAuth(applicationContext).getStudentActivityDetail(activity.id).enqueue(object : Callback<GetActivityDetailResponse> {
                    override fun onResponse(
                        call: Call<GetActivityDetailResponse>,
                        response: Response<GetActivityDetailResponse>
                    ) {
                        if (response.isSuccessful) {
                            val data = response.body()?.data
                            if (data == null) {
                                Alert.showError(
                                    this@CreateAppointmentActivity,
                                    "Gagal mendapatkan detail kegiatan!",
                                    "coba lagi"
                                )
                                finish()
                                return
                            }
                            listCheckLectureAdapter.addList(data.lectures.map { CheckLecture(it.user.name, false, it.user.id) })
                            return
                        } else {
                            Alert.showError(
                                this@CreateAppointmentActivity,
                                "Gagal mendapatkan detail kegiatan!",
                                "Periksa koneksi internet anda dan coba lagi [1]"
                            )
                            finish()
                        }
                    }

                    override fun onFailure(call: Call<GetActivityDetailResponse>, t: Throwable) {
                        Alert.showError(
                            this@CreateAppointmentActivity,
                            "Gagal mendapatkan detail kegiatan!",
                            "Periksa koneksi internet anda dan coba lagi [2]"
                        )
                        finish()
                    }
                })
            }
        }
    }

    private fun resetError() {
        b.activityError.text = ""
        b.titleError.text = ""
        b.dateError.text = ""
        b.descriptionError.text = ""
        b.locationError.text = ""
        b.lectureError.text = ""
    }
    private fun submitInput() {
        resetError()

        val selectedItems = listCheckLectureAdapter.getCheckedList()

        requestLectureIds = selectedItems.map { it.id } as ArrayList<Int>
        requestTitle = b.titleInput.text.toString()
        requestDate = b.dateInput.text.toString()
        requestDescription = b.descriptionInput.text.toString()
        requestLocation = b.locationInput.text.toString()

        val request = CreateAppointmentRequest(
            requestTitle, requestDate, requestDescription, requestLocation, requestLectureIds
        )

        RetrofitClient.callAuth(applicationContext).postStudentAppointment(requestActivityId, request).enqueue(object : Callback<MessageResponse> {
            override fun onResponse(
                call: Call<MessageResponse>,
                response: Response<MessageResponse>
            ) {
                if (!response.isSuccessful) {
                    val responseError = RetrofitClient.mapJsonToDataClass<ErrorResponse>(response.errorBody())
                        ?: return

                    Alert.showError(
                        this@CreateAppointmentActivity,
                        "Gagal membuat bimbingan!",
                        responseError.message.toString())

                    responseError.errors?.forEach { (key, value) ->
                        if (key == "activity_id") {
                            b.activityError.text = value.joinToString(",")
                        }

                        if (key == "title") {
                            b.titleError.text = value.joinToString(",")
                        }

                        if (key == "date") {
                            b.dateError.text = value.joinToString(",")
                        }

                        if (key == "description") {
                            b.descriptionError.text = value.joinToString(",")
                        }

                        if (key == "location") {
                            b.locationError.text = value.joinToString(",")
                        }

                        if (key == "lecture_ids") {
                            b.lectureError.text = value.joinToString(",")
                        }
                    }

                    return
                }
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                Alert.showError(
                    this@CreateAppointmentActivity,
                    "Gagal membuat bimbingan!",
                    "Periksa koneksi internet anda dan coba lagi"
                )
            }

        })
    }
}