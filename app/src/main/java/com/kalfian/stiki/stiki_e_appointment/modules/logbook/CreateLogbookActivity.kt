package com.kalfian.stiki.stiki_e_appointment.modules.logbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.core.content.res.ResourcesCompat
import com.kalfian.stiki.stiki_e_appointment.R
import com.kalfian.stiki.stiki_e_appointment.databinding.ActivityCreateLogbookBinding
import com.kalfian.stiki.stiki_e_appointment.models.global.ErrorResponse
import com.kalfian.stiki.stiki_e_appointment.models.global.MessageResponse
import com.kalfian.stiki.stiki_e_appointment.requests.CreateLogbookRequest
import com.kalfian.stiki.stiki_e_appointment.utils.Alert
import com.kalfian.stiki.stiki_e_appointment.utils.Constant
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

    private var requestActivityId: Int = 0
    private var requestUserId: Int = 0
    private var requestDate: String = ""
    private var requestDescription: String = ""
    private var requestProblem: String? = null
    private var requestProof: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        b = ActivityCreateLogbookBinding.inflate(layoutInflater)
        val v = b.root
        requestActivityId = intent.getIntExtra(Constant.DETAIL_ACTIVITY_ID, 0)
        overlayLoader = OverlayLoader(this)
        setContentView(v)

        if (requestActivityId == 0) {
            Alert.showError(this, "Gagal membuka halaman!", "Periksa koneksi internet anda dan coba lagi")
            finish()
        }

        setupLayout()

        b.btnCreateLogbook.setOnClickListener {
            submitForm()
        }
    }

    private fun submitForm() {
        requestDescription = b.logbookDescription.text.toString()
        requestUserId = SharedPreferenceUtil.retrieve(applicationContext, "id", "0").toInt()
        if (!b.logbookProblem.text.isNullOrEmpty()) requestProblem = b.logbookProblem.text.toString()
        if (!b.logbookProof.text.isNullOrEmpty()) requestProof = b.logbookProof.text.toString()

        val request = CreateLogbookRequest(
            requestDate,
            requestDescription,
            requestProblem,
            requestProof
        )

        RetrofitClient.callAuth(applicationContext).postStudentLogbook(requestActivityId, request).enqueue(object :
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
                        "Gagal membuat bimbingan!",
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

    private fun setupLayout() {
        b.nav.headerTitle.text = "Tambah Logbook"
        b.nav.backButton.setOnClickListener {
            finish()
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
}