package com.kalfian.stiki.stiki_e_appointment.modules.appointment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony.Mms.Part
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.kalfian.stiki.stiki_e_appointment.adapters.ListParticipantAdapter
import com.kalfian.stiki.stiki_e_appointment.databinding.ActivityDetailAppointmentBinding
import com.kalfian.stiki.stiki_e_appointment.models.Participant
import com.kalfian.stiki.stiki_e_appointment.modules.chat.ChatActivity
import com.kalfian.stiki.stiki_e_appointment.modules.logbook.DetailLogbookLectureActivity
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

        b.btnChat.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra(Constant.IS_LECTURE, isLecture)
            startActivity(intent)
        }

        b.btnChangeStatusLogbook.setOnClickListener {
            val dialog = BottomSheetDialog(this, R.style.Theme_Design_Light_BottomSheetDialog)
            val dialogView = LayoutInflater.from(applicationContext).inflate(
                com.kalfian.stiki.stiki_e_appointment.R.layout.status_bottom_sheet,
                findViewById<LinearLayout>(com.kalfian.stiki.stiki_e_appointment.R.id.status_bottom_sheet)
            )

            dialogView.findViewById<View>(com.kalfian.stiki.stiki_e_appointment.R.id.bottom_close_btn).setOnClickListener {
                dialog.dismiss()
            }

            dialog.setContentView(dialogView)
            dialog.show()
        }

    }

    override fun onItemClickListener(data: Participant) {

    }

    override fun onBtnLogbookClickListener(data: Participant) {

    }
}