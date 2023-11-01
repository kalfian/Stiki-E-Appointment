package com.kalfian.stiki.stiki_e_appointment.modules.logbook

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.content.res.ResourcesCompat
import com.kalfian.stiki.stiki_e_appointment.R
import com.kalfian.stiki.stiki_e_appointment.databinding.ActivityCreateLogbookBinding
import com.kalfian.stiki.stiki_e_appointment.models.Logbook
import com.kalfian.stiki.stiki_e_appointment.models.global.ErrorResponse
import com.kalfian.stiki.stiki_e_appointment.models.global.MessageResponse
import com.kalfian.stiki.stiki_e_appointment.models.logbookResponse.GetLogbookDetailResponse
import com.kalfian.stiki.stiki_e_appointment.models.logbookResponse.GetLogbooksResponse
import com.kalfian.stiki.stiki_e_appointment.models.requests.CreateLogbookRequest
import com.kalfian.stiki.stiki_e_appointment.models.requests.UpdateLogbookStudentRequest
import com.kalfian.stiki.stiki_e_appointment.utils.Alert
import com.kalfian.stiki.stiki_e_appointment.utils.Constant
import com.kalfian.stiki.stiki_e_appointment.utils.Helper
import com.kalfian.stiki.stiki_e_appointment.utils.OverlayLoader
import com.kalfian.stiki.stiki_e_appointment.utils.RetrofitClient
import com.kalfian.stiki.stiki_e_appointment.utils.SharedPreferenceUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import setupDateTimePicker

class CreateLogbookActivity : AppCompatActivity() {

    private lateinit var b: ActivityCreateLogbookBinding
    private lateinit var overlayLoader: OverlayLoader

    private var logbookId = 0
    private var isUpdate = false
    private var isLecture = false

    private var activityId: Int = 0
    private var requestUserId: Int = 0
    private var requestDate: String = ""
    private var requestDescription: String = ""
    private var requestProblem: String? = null
    private var requestProof: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        b = ActivityCreateLogbookBinding.inflate(layoutInflater)
        val v = b.root
        isLecture = Helper.stringToBoolean(SharedPreferenceUtil.retrieve(applicationContext, Constant.SHARED_IS_LECTURE, "false"))

        activityId = intent.getIntExtra(Constant.DETAIL_ACTIVITY_ID, 0)
        logbookId = intent.getIntExtra(Constant.DETAIL_LOGBOOK_ID, 0)
        if (logbookId != 0) isUpdate = true

        overlayLoader = OverlayLoader(this)
        setContentView(v)

        if (activityId == 0) {
            Alert.showError(this, "Gagal membuka halaman!", "Periksa koneksi internet anda dan coba lagi")
            finish()
        }

        setupLayout(isUpdate)

        b.swipeRefreshCreateLogbook.setOnRefreshListener {
            b.swipeRefreshCreateLogbook.isRefreshing = false
        }

        b.btnCreateLogbook.setOnClickListener {
            submitForm()
        }
    }

    private fun submitForm() {
        requestDescription = b.logbookDescription.text.toString()
        requestUserId = SharedPreferenceUtil.retrieve(applicationContext, "id", "0").toInt()
        if (!b.logbookProblem.text.isNullOrEmpty()) requestProblem = b.logbookProblem.text.toString()
        if (!b.logbookProof.text.isNullOrEmpty()) requestProof = b.logbookProof.text.toString()

        if(isUpdate) {
            updateLogbook()
        } else {
            createLogbookStudent()
        }

    }

    private fun createLogbookStudent() {
        val request = CreateLogbookRequest(
            requestDate,
            requestDescription,
            requestProblem,
            requestProof
        )

        RetrofitClient.callAuth(applicationContext).postStudentLogbook(activityId, request).enqueue(object :
            Callback<MessageResponse> {
            override fun onResponse(
                call: Call<MessageResponse>,
                response: Response<MessageResponse>
            ) {
                if (!response.isSuccessful) {
                    val responseError = RetrofitClient.mapJsonToDataClass<ErrorResponse>(response.errorBody())
                        ?: return

                    Alert.showError(
                        this@CreateLogbookActivity,
                        "Gagal memuat logbook!",
                        responseError.message.toString())

                    responseError.errors?.forEach { (key, value) ->
//                        if (key == "activity_id") {
//                            b.activityError.text = value.joinToString(",")
//                        }
                    }

                    return
                }

                Alert.showSuccess(
                    this@CreateLogbookActivity,
                    "Berhasil membuat logbook!",
                    "Logbook anda telah berhasil dibuat"
                )

                val resultIntent = Intent()
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                Alert.showError(
                    this@CreateLogbookActivity,
                    "Gagal membuat logbook!",
                    "Periksa koneksi internet anda dan coba lagi"
                )
            }

        })
    }

    private fun setupLayout(isUpdate: Boolean) {
        b.nav.headerTitle.text = "Tambah Logbook"
        b.nav.backButton.setOnClickListener {
            finish()
        }

        if (isUpdate) {
            b.btnCreateLogbook.text = "Update Logbook"
            b.nav.headerTitle.text = "Update Logbook"
            getLogbook()
        }

        b.logbookDate.setupDateTimePicker(this) { selected, selectedDB ->
            b.logbookDate.setText(selected)
            requestDate = selectedDB
        }

        b.logbookDescription.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER)) {
                // Insert a newline character when the Enter key is pressed
                b.logbookDescription.text?.insert(b.logbookDescription.selectionStart, "\n")
                return@setOnEditorActionListener true
            }
            false
        }

        b.logbookProblem.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER)) {
                // Insert a newline character when the Enter key is pressed
                b.logbookProblem.text?.insert(b.logbookProblem.selectionStart, "\n")
                return@setOnEditorActionListener true
            }
            false
        }

        b.logbookProof.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER)) {
                // Insert a newline character when the Enter key is pressed
                b.logbookProof.text?.insert(b.logbookProof.selectionStart, "\n")
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun getLogbook() {
        if(isLecture) {
            getLogbookLecture()
        } else {
            getLogbookStudent()
        }
    }

    private fun getLogbookStudent() {
        overlayLoader.show()
        RetrofitClient.callAuth(applicationContext).getStudentLogbookById(activityId, logbookId).enqueue(object :
            Callback<GetLogbookDetailResponse> {
            override fun onResponse(
                call: Call<GetLogbookDetailResponse>,
                response: Response<GetLogbookDetailResponse>
            ) {
                if (!response.isSuccessful) {
                    val responseError = RetrofitClient.mapJsonToDataClass<ErrorResponse>(response.errorBody())
                        ?: return

                    Alert.showError(
                        this@CreateLogbookActivity,
                        "Gagal mengambil logbook!",
                        responseError.message.toString())
                    overlayLoader.hide()
                    finish()
                    return
                }

                val data = response.body()?.data ?: return
                setLogbook(data)
                overlayLoader.hide()

            }

            override fun onFailure(call: Call<GetLogbookDetailResponse>, t: Throwable) {
                Alert.showError(
                    this@CreateLogbookActivity,
                    "Gagal mengambil data logbook!",
                    "Periksa koneksi internet anda dan coba lagi"
                )
                overlayLoader.hide()
                finish()
            }

        })
    }

    private fun setLogbook(data: Logbook) {
        requestDate = data.dateDB
        b.logbookDate.setText(data.date)
        b.logbookDescription.setText(data.description)
        b.logbookProblem.setText(data.problem)
        b.logbookProof.setText(data.logbookProof)
    }
    private fun getLogbookLecture() {

    }

    private fun updateLogbook() {
        if(isLecture) {
            updateLogbookLecture()
        } else {
            updateLogbookStudent()
        }
    }

    private fun updateLogbookLecture() {

    }

    private  fun updateLogbookStudent() {
        val request = UpdateLogbookStudentRequest(
            requestDate,
            requestDescription,
            requestProblem,
            requestProof
        )

        RetrofitClient.callAuth(applicationContext).updateStudentLogbook(activityId, logbookId, request).enqueue(object :
            Callback<MessageResponse> {
            override fun onResponse(
                call: Call<MessageResponse>,
                response: Response<MessageResponse>
            ) {
                if (!response.isSuccessful) {
                    val responseError = RetrofitClient.mapJsonToDataClass<ErrorResponse>(response.errorBody())
                        ?: return

                    Alert.showError(
                        this@CreateLogbookActivity,
                        "Gagal memperbarui logbook!",
                        responseError.message.toString())

                    return
                }

                Alert.showSuccess(
                    this@CreateLogbookActivity,
                    "Berhasil memperbarui logbook!",
                    "Logbook anda telah berhasil diperbarui"
                )

                val resultIntent = Intent()
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                Alert.showError(
                    this@CreateLogbookActivity,
                    "Gagal memperbarui logbook!",
                    "Periksa koneksi internet anda dan coba lagi"
                )
            }

        })
    }
}