package com.kalfian.stiki.stiki_e_appointment.modules.logbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.kalfian.stiki.stiki_e_appointment.R
import com.kalfian.stiki.stiki_e_appointment.adapters.ListBottomSheetButtonAdapter
import com.kalfian.stiki.stiki_e_appointment.adapters.ListLogbookAdapter
import com.kalfian.stiki.stiki_e_appointment.databinding.ActivityDetailLogbookLectureBinding
import com.kalfian.stiki.stiki_e_appointment.models.BottomSheetButton
import com.kalfian.stiki.stiki_e_appointment.models.Logbook
import com.kalfian.stiki.stiki_e_appointment.models.activityResponse.GetActivityDetailResponse
import com.kalfian.stiki.stiki_e_appointment.models.logbookResponse.GetLogbooksResponse
import com.kalfian.stiki.stiki_e_appointment.utils.Alert
import com.kalfian.stiki.stiki_e_appointment.utils.BottomSheetRequest
import com.kalfian.stiki.stiki_e_appointment.utils.Constant
import com.kalfian.stiki.stiki_e_appointment.utils.Helper
import com.kalfian.stiki.stiki_e_appointment.utils.OverlayLoader
import com.kalfian.stiki.stiki_e_appointment.utils.RetrofitClient
import com.kalfian.stiki.stiki_e_appointment.utils.SharedPreferenceUtil
import com.kalfian.stiki.stiki_e_appointment.utils.bottomSheet
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class LogbookLectureActivity : AppCompatActivity(), ListLogbookAdapter.AdapterLogbookOnClickListener {

    private lateinit var b: ActivityDetailLogbookLectureBinding
    private lateinit var logbookAdapter: ListLogbookAdapter
    private lateinit var overlayLoader: OverlayLoader
    private lateinit var bottomSheet: BottomSheetDialog

    private var activityName = ""
    private var activityId = 0
    private var participantId = 0
    private var participantName = ""
    private var participantIdentity = ""
    private var isLecture: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityDetailLogbookLectureBinding.inflate(layoutInflater)
        val v = b.root

        overlayLoader = OverlayLoader(this)
        activityId = intent.getIntExtra(Constant.DETAIL_ACTIVITY_ID, 0)
        participantId = intent.getIntExtra(Constant.DETAIL_PARTICIPANT_ID, 0)
        participantName = intent.getStringExtra(Constant.DETAIL_PARTICIPANT_NAME) ?: "-"
        participantIdentity = intent.getStringExtra(Constant.DETAIL_PARTICIPANT_IDENTITY) ?: "-"


        isLecture = Helper.stringToBoolean(SharedPreferenceUtil.retrieve(applicationContext, Constant.SHARED_IS_LECTURE, "false"))

        setContentView(v)

        if (activityId == 0) {
            finish()
        }

        if (participantId == 0) {
            finish()
        }

        b.nav.backButton.setOnClickListener {
            finish()
        }

        setupListLogbook()
        fetchActivityAndLogbook()

        b.swipeRefreshLogbook.setOnRefreshListener {
            fetchActivityAndLogbook()
            b.swipeRefreshLogbook.isRefreshing = false
        }
    }

    private fun setupListLogbook() {
        val lm = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        b.recyclerListLogbook.layoutManager = lm
        logbookAdapter = ListLogbookAdapter(this )
        b.recyclerListLogbook.adapter = logbookAdapter
    }

    private fun setupActivity() {
        RetrofitClient.callAuth(applicationContext).getLectureActivityDetail(activityId).enqueue(object: retrofit2.Callback<GetActivityDetailResponse> {
            override fun onResponse(call: retrofit2.Call<GetActivityDetailResponse>, response: retrofit2.Response<GetActivityDetailResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data == null) {
                        Alert.showError(this@LogbookLectureActivity, "Gagal membuka halaman!", "Periksa koneksi internet anda dan coba lagi")
                        finish()
                        return
                    }

                    val activity = data.data
                    activityName = activity.name
                    b.nav.headerTitle.text = "Logbook ${activityName}"
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
                        .error(R.drawable.no_image_stiki)
                        .into(b.activityBanner)
                    b.activityTitle.text = activity.name
                    b.activityLocation.text = activity.location
                    b.activityDate.text = buildString {
                        append(activity.startDate)
                        append("-")
                        append(activity.endDate)
                    }

                    b.participantName.text = participantName
                    b.participantIdentity.text = participantIdentity
                }
            }

            override fun onFailure(call: retrofit2.Call<GetActivityDetailResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun fetchActivityAndLogbook() {
        overlayLoader.show()
        runBlocking {
            val runActivity = async{ setupActivity() }
            val runGetLogbook = async{ getListLogbook() }

            try {
                runActivity.await()
                runGetLogbook.await()

                overlayLoader.hide()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getListLogbook() {
        logbookAdapter.clear()
        RetrofitClient.callAuth(applicationContext).getLectureLogbookByActivity(activityId, userId = participantId).enqueue(object: retrofit2.Callback<GetLogbooksResponse> {
            override fun onResponse(call: retrofit2.Call<GetLogbooksResponse>, response: retrofit2.Response<GetLogbooksResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data == null) {
                        Alert.showError(this@LogbookLectureActivity, "Gagal membuka halaman!", "Periksa koneksi internet anda dan coba lagi")
                        finish()
                        return
                    }

                    val logbooks = data.data
                    logbookAdapter.addList(logbooks)
                }
            }

            override fun onFailure(call: retrofit2.Call<GetLogbooksResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    override fun onItemClickListener(data: Logbook) {

    }

    override fun onChangeStatusCliclListener(data: Logbook) {
        val request = BottomSheetRequest(
            ctx = this,
            title = "Ubah Komentar",
            okTitle = "Ok",
            btnOkOnClick = {
                saveComment(data.id, it) {
                    bottomSheet.dismiss()
                    getListLogbook()
                }
            },
            useInput = true,
            inputHint = "Ubah Komentar",
            inputValue = data.lectureComment
        )

        bottomSheet = bottomSheet(request)
    }

    private fun saveComment(id: Int, comment: String, callback: () -> Unit) {
        callback()
    }
}