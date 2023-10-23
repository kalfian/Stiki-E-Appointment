package com.kalfian.stiki.stiki_e_appointment.modules.logbook

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.kalfian.stiki.stiki_e_appointment.adapters.ListBottomSheetButtonAdapter
import com.kalfian.stiki.stiki_e_appointment.adapters.ListLogbookAdapter
import com.kalfian.stiki.stiki_e_appointment.databinding.ActivityDetailLogbookStudentBinding
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

class LogbookStudentActivity : AppCompatActivity(), ListLogbookAdapter.AdapterLogbookOnClickListener, ListBottomSheetButtonAdapter.AdapterBottomSheetButton {

    private lateinit var b: ActivityDetailLogbookStudentBinding
    private lateinit var logbookAdapter: ListLogbookAdapter
    private lateinit var overlayLoader: OverlayLoader
    private lateinit var bottomSheet: BottomSheetDialog

    private var activityName = ""
    private var activityId = 0
    private var isLecture: Boolean = false

    private lateinit var startCreateActivity: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityDetailLogbookStudentBinding.inflate(layoutInflater)
        val v = b.root
        overlayLoader = OverlayLoader(this)

        startCreateActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                fetchActivityAndLogbook()
            }
        }

        activityId = intent.getIntExtra(Constant.DETAIL_ACTIVITY_ID, 0)
        isLecture = Helper.stringToBoolean(SharedPreferenceUtil.retrieve(applicationContext, Constant.SHARED_IS_LECTURE, "false"))

        setContentView(v)

        if (activityId == 0) {
            finish()
        }

        b.nav.backButton.setOnClickListener {
            finish()
        }

        setupListLogbook()
        fetchActivityAndLogbook()

        b.swipeRefreshLogbookStudent.setOnRefreshListener {
            fetchActivityAndLogbook()
            b.swipeRefreshLogbookStudent.isRefreshing = false
        }

        b.nav.btnPlus.setOnClickListener {
            val intent = Intent(this, CreateLogbookActivity::class.java)
            intent.putExtra(Constant.DETAIL_ACTIVITY_ID, activityId)
            startCreateActivity.launch(intent)
        }
    }

    private fun setupActivity() {
        RetrofitClient.callAuth(applicationContext).getStudentActivityDetail(activityId).enqueue(object: retrofit2.Callback<GetActivityDetailResponse> {
            override fun onResponse(call: retrofit2.Call<GetActivityDetailResponse>, response: retrofit2.Response<GetActivityDetailResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data == null) {
                        Alert.showError(this@LogbookStudentActivity, "Gagal membuka halaman!", "Periksa koneksi internet anda dan coba lagi")
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
                        .error(com.kalfian.stiki.stiki_e_appointment.R.drawable.noimage)
                        .into(b.activityBanner)
                    b.activityTitle.text = activity.name
                    b.activityLocation.text = activity.location
                    b.activityDate.text = buildString {
                        append(activity.startDate)
                        append("-")
                        append(activity.endDate)
                    }
                }
            }

            override fun onFailure(call: retrofit2.Call<GetActivityDetailResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun setupListLogbook() {
        val lm = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        b.recyclerListLogbook.layoutManager = lm
        logbookAdapter = ListLogbookAdapter(this )
        b.recyclerListLogbook.adapter = logbookAdapter
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
        RetrofitClient.callAuth(applicationContext).getStudentLogbookByActivity(activityId).enqueue(object: retrofit2.Callback<GetLogbooksResponse> {
            override fun onResponse(call: retrofit2.Call<GetLogbooksResponse>, response: retrofit2.Response<GetLogbooksResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data == null) {
                        Alert.showError(this@LogbookStudentActivity, "Gagal membuka halaman!", "Periksa koneksi internet anda dan coba lagi")
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
        val listButtonAdapter = ListBottomSheetButtonAdapter(this)
        listButtonAdapter.add(BottomSheetButton(1, "Edit Logbook", data.id.toString()))

        val request = BottomSheetRequest(
            ctx = this,
            title = "Kelola Logbook",
            okTitle = "Ubah",
            disableOkButton = true,
            btnOkOnClick = {

            },
            recyclerViewAdapter = listButtonAdapter
        )

        bottomSheet = bottomSheet(request)
    }

    override fun onChangeStatusCliclListener(data: Logbook) {

    }

    override fun onItemClickListener(data: BottomSheetButton) {
        when (data.id) {
            1 -> {
                val intent = Intent(this, CreateLogbookActivity::class.java)
                intent.putExtra(Constant.DETAIL_ACTIVITY_ID, activityId)
                intent.putExtra(Constant.DETAIL_LOGBOOK_ID, data.payload.toInt())
                startCreateActivity.launch(intent)
            }
        }
        bottomSheet.dismiss()
    }
}