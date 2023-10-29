package com.kalfian.stiki.stiki_e_appointment.modules.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.kalfian.stiki.stiki_e_appointment.R
import com.kalfian.stiki.stiki_e_appointment.adapters.ListParticipantAdapter
import com.kalfian.stiki.stiki_e_appointment.databinding.ActivityDetailActivityBinding
import com.kalfian.stiki.stiki_e_appointment.models.Activity
import com.kalfian.stiki.stiki_e_appointment.models.Participant
import com.kalfian.stiki.stiki_e_appointment.models.activityResponse.GetActivityDetailResponse
import com.kalfian.stiki.stiki_e_appointment.modules.logbook.LogbookLectureActivity
import com.kalfian.stiki.stiki_e_appointment.modules.logbook.LogbookStudentActivity
import com.kalfian.stiki.stiki_e_appointment.utils.Alert
import com.kalfian.stiki.stiki_e_appointment.utils.Constant
import com.kalfian.stiki.stiki_e_appointment.utils.Helper
import com.kalfian.stiki.stiki_e_appointment.utils.OverlayLoader
import com.kalfian.stiki.stiki_e_appointment.utils.RetrofitClient
import com.kalfian.stiki.stiki_e_appointment.utils.SharedPreferenceUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivityActivity : AppCompatActivity(), ListParticipantAdapter.AdapterParticipantOnClickListener {

    private lateinit var b: ActivityDetailActivityBinding
    private lateinit var participantAdapter: ListParticipantAdapter
    private var isLecture: Boolean = false

    var id = 0
    private lateinit var overlayLoader: OverlayLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityDetailActivityBinding.inflate(layoutInflater)
        val v = b.root
        setContentView(v)
        overlayLoader = OverlayLoader(this)

        isLecture = Helper.stringToBoolean(SharedPreferenceUtil.retrieve(applicationContext, Constant.SHARED_IS_LECTURE, "false"))
        id = intent.getIntExtra(Constant.DETAIL_ACTIVITY_ID, id)

        if (id == 0) {
            finish()
        }

        b.nav.backButton.setOnClickListener {
            finish()
        }

        if (isLecture) {
            b.btnGoLogbook.visibility = View.GONE
        } else {
            b.btnGoLogbook.visibility = View.VISIBLE
        }

        setupPage(isLecture)

        setupListParticipant()

        b.swipeRefreshDetailActivity.setOnRefreshListener {
            setupPage(isLecture)
            b.swipeRefreshDetailActivity.isRefreshing = false
        }

        b.btnGoLogbook.setOnClickListener{
            val intent = Intent(this, LogbookStudentActivity::class.java)
            intent.putExtra(Constant.DETAIL_ACTIVITY_ID, id)
            startActivity(intent)
        }
    }

    private fun setupPage(isLecture: Boolean) {
        overlayLoader.show()
        if (isLecture) {
            setupLecturePage()
        } else {
            setupStudentPage()
        }
    }

    private fun setupLecturePage() {
        RetrofitClient.callAuth(applicationContext).getLectureActivityDetail(id).enqueue(object : Callback<GetActivityDetailResponse> {
            override fun onResponse(
                call: Call<GetActivityDetailResponse>,
                response: Response<GetActivityDetailResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    if (data == null) {
                        Alert.showError(
                            this@DetailActivityActivity,
                            "Gagal mendapatkan detail kegiatan!", "coba lagi"
                        )
                        finish()
                        return
                    }

                    attachData(data)

                    overlayLoader.hide()
                    return
                } else {
                    overlayLoader.hide()
                    Alert.showError(
                        this@DetailActivityActivity,
                        "Gagal mendapatkan detail kegiatan!", "Periksa koneksi internet anda dan coba lagi [1]"
                    )
                    finish()
                }
            }

            override fun onFailure(call: Call<GetActivityDetailResponse>, t: Throwable) {

                overlayLoader.hide()

                t.let {
                    Log.d("TESTING_ERROR", it.message.toString())
                    Alert.showError(
                        this@DetailActivityActivity,
                        "Gagal mendapatkan detail kegiatan!", "Periksa koneksi internet anda dan coba lagi"
                    )
                }

                finish()
            }

        })
    }
    private fun setupStudentPage() {
        RetrofitClient.callAuth(applicationContext).getStudentActivityDetail(id).enqueue(object : Callback<GetActivityDetailResponse> {
            override fun onResponse(
                call: Call<GetActivityDetailResponse>,
                response: Response<GetActivityDetailResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    if (data == null) {
                        Alert.showError(
                            this@DetailActivityActivity,
                            "Gagal mendapatkan detail kegiatan!", "coba lagi"
                        )
                        finish()
                        return
                    }

                    attachData(data)

                    overlayLoader.hide()
                    return
                } else {
                    overlayLoader.hide()
                    Alert.showError(
                        this@DetailActivityActivity,
                        "Gagal mendapatkan detail kegiatan!", "Periksa koneksi internet anda dan coba lagi [1]"
                    )
                    finish()
                }
            }

            override fun onFailure(call: Call<GetActivityDetailResponse>, t: Throwable) {

                overlayLoader.hide()

                t.let {
                    Log.d("TESTING_ERROR", it.message.toString())
                    Alert.showError(
                        this@DetailActivityActivity,
                        "Gagal mendapatkan detail kegiatan!", "Periksa koneksi internet anda dan coba lagi"
                    )
                }

                finish()
            }

        })
    }

    private fun attachData(data: Activity) {
        Glide.with(applicationContext)
            .load(data.banner)
            .placeholder(CircularProgressDrawable(applicationContext).apply {
                setColorSchemeColors(
                    ContextCompat.getColor(applicationContext, R.color.dark_blue)
                )
                strokeWidth = 2f
                centerRadius = 10f
                start()
            })
            .error(R.drawable.no_image_stiki)
            .into(b.activityBanner)
        b.nav.headerTitle.text = data.name
        b.activityTitle.text = data.name
        b.activityLocation.text = data.location
        b.activityDate.text = buildString {
            append(data.startDate)
            append("-")
            append(data.endDate)
        }

        val spannedHtml = Html.fromHtml(data.description, Html.FROM_HTML_MODE_LEGACY)
        b.activityDescription.text = spannedHtml

        getListParticipant(data.students)
    }
    private fun setupListParticipant() {
        val lm = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        b.recyclerListParticipant.layoutManager = lm
        participantAdapter = ListParticipantAdapter(this, isLecture)
        b.recyclerListParticipant.adapter = participantAdapter
    }

    private fun getListParticipant(participants: ArrayList<Participant> = arrayListOf()) {
        participantAdapter.clear()
        participantAdapter.addList(participants)
    }

    override fun onItemClickListener(data: Participant) {

    }

    override fun onBtnLogbookClickListener(data: Participant) {
        if (isLecture) {
            val intent = Intent(this, LogbookLectureActivity::class.java)
            intent.putExtra(Constant.DETAIL_ACTIVITY_ID, id)
            intent.putExtra(Constant.DETAIL_PARTICIPANT_ID, data.user.id)
            intent.putExtra(Constant.DETAIL_PARTICIPANT_NAME, data.user.name)
            intent.putExtra(Constant.DETAIL_PARTICIPANT_IDENTITY, data.user.identity)
            startActivity(intent)
            return
        }
    }
}