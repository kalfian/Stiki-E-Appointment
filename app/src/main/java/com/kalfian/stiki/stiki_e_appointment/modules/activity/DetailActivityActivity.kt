package com.kalfian.stiki.stiki_e_appointment.modules.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.kalfian.stiki.stiki_e_appointment.R
import com.kalfian.stiki.stiki_e_appointment.adapters.ListParticipantAdapter
import com.kalfian.stiki.stiki_e_appointment.databinding.ActivityDetailActivityBinding
import com.kalfian.stiki.stiki_e_appointment.models.Participant
import com.kalfian.stiki.stiki_e_appointment.models.activity_response.GetActivityDetailResponse
import com.kalfian.stiki.stiki_e_appointment.modules.logbook.DetailLogbookLectureActivity
import com.kalfian.stiki.stiki_e_appointment.modules.logbook.DetailLogbookStudentActivity
import com.kalfian.stiki.stiki_e_appointment.utils.Alert
import com.kalfian.stiki.stiki_e_appointment.utils.Constant
import com.kalfian.stiki.stiki_e_appointment.utils.OverlayLoader
import com.kalfian.stiki.stiki_e_appointment.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import www.sanju.motiontoast.MotionToast

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

        isLecture = intent.getBooleanExtra(Constant.IS_LECTURE, isLecture)
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

        setupPage()

        setupListParticipant()

        b.swipeRefreshDetailActivity.setOnRefreshListener {
            setupPage()
            b.swipeRefreshDetailActivity.isRefreshing = false
        }

        b.btnGoLogbook.setOnClickListener{
            val intent = Intent(this, DetailLogbookStudentActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupPage() {
        overlayLoader.show()

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
                        .error(R.drawable.noimage)
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
            val intent = Intent(this, DetailLogbookLectureActivity::class.java)
            startActivity(intent)
            return
        }

        val intent = Intent(this, DetailLogbookStudentActivity::class.java)
        startActivity(intent)
    }
}