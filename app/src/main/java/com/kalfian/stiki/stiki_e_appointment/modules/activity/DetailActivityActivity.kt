package com.kalfian.stiki.stiki_e_appointment.modules.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.kalfian.stiki.stiki_e_appointment.adapters.ListParticipantAdapter
import com.kalfian.stiki.stiki_e_appointment.databinding.ActivityDetailActivityBinding
import com.kalfian.stiki.stiki_e_appointment.models.Participant
import com.kalfian.stiki.stiki_e_appointment.modules.logbook.DetailLogbookLectureActivity
import com.kalfian.stiki.stiki_e_appointment.modules.logbook.DetailLogbookStudentActivity
import com.kalfian.stiki.stiki_e_appointment.utils.Constant

class DetailActivityActivity : AppCompatActivity(), ListParticipantAdapter.AdapterParticipantOnClickListener {

    private lateinit var b: ActivityDetailActivityBinding
    private lateinit var participantAdapter: ListParticipantAdapter
    private var isLecture: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityDetailActivityBinding.inflate(layoutInflater)
        val v = b.root
        setContentView(v)

        isLecture = intent.getBooleanExtra(Constant.IS_LECTURE, isLecture)

        b.nav.headerTitle.text = "PT Taspen"
        b.nav.backButton.setOnClickListener {
            finish()
        }

        if (isLecture) {
            b.btnGoLogbook.visibility = View.GONE
        } else {
            b.btnGoLogbook.visibility = View.VISIBLE
        }

        setupListParticipant()
        getListParticipant()

        b.btnGoLogbook.setOnClickListener{
            val intent = Intent(this, DetailLogbookStudentActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupListParticipant() {
        val lm = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        b.recyclerListParticipant.layoutManager = lm
        participantAdapter = ListParticipantAdapter(this, isLecture)
        b.recyclerListParticipant.adapter = participantAdapter
    }

    private fun getListParticipant() {
        participantAdapter.clear()
        participantAdapter.add(Participant("1", "Kukuh Alfian Hanif"))
        participantAdapter.add(Participant("1", "Kukuh Alfian Hanif"))
        participantAdapter.add(Participant("1", "Kukuh Alfian Hanif"))
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