package com.kalfian.stiki.stiki_e_appointment.modules.appointment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony.Mms.Part
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.kalfian.stiki.stiki_e_appointment.adapters.ListParticipantAdapter
import com.kalfian.stiki.stiki_e_appointment.databinding.ActivityDetailAppointmentBinding
import com.kalfian.stiki.stiki_e_appointment.models.Participant
import com.kalfian.stiki.stiki_e_appointment.utils.Constant

class DetailAppointmentActivity : AppCompatActivity(), ListParticipantAdapter.AdapterParticipantOnClickListener {


    private lateinit var b: ActivityDetailAppointmentBinding
    private lateinit var participantAdapter: ListParticipantAdapter

    private var isLecture: Boolean = false
    private var isShowLogBook: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityDetailAppointmentBinding.inflate(layoutInflater)
        val v = b.root
        setContentView(v)

        isLecture = intent.getBooleanExtra(Constant.IS_LECTURE, isLecture)

        if (!isLecture) {
            b.containerListParticipant.visibility = View.GONE
            b.btnChangeStatusLogbook.visibility = View.GONE
        } else {
            b.containerListParticipant.visibility = View.VISIBLE
            val lm = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
            b.recyclerListParticipant.layoutManager = lm

            participantAdapter = ListParticipantAdapter(this, isShowLogBook)
            b.recyclerListParticipant.adapter = participantAdapter

            participantAdapter.clear()
            participantAdapter.addList(listOf(
                Participant("1", "1", "1")
            ))
        }

        b.nav.headerTitle.text = "Bimbingan 1"
        b.nav.backButton.setOnClickListener {
            finish()
        }
    }

    override fun onItemClickListener(data: Participant) {

    }

    override fun onBtnLogbookClickListener(data: Participant) {

    }
}