package com.kalfian.stiki.stiki_e_appointment.modules.student

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.kalfian.stiki.stiki_e_appointment.adapters.ListActivityAdapter
import com.kalfian.stiki.stiki_e_appointment.adapters.ListParticipantAdapter
import com.kalfian.stiki.stiki_e_appointment.databinding.ActivityDetailActivityBinding
import com.kalfian.stiki.stiki_e_appointment.models.Activity
import com.kalfian.stiki.stiki_e_appointment.models.Participant

class DetailActivityActivity : AppCompatActivity(), ListParticipantAdapter.AdapterParticipantOnClickListener {

    private lateinit var b: ActivityDetailActivityBinding
    private lateinit var participantAdapter: ListParticipantAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityDetailActivityBinding.inflate(layoutInflater)
        val v = b.root
        setContentView(v)

        b.nav.headerTitle.text = "PT Taspen"
        b.nav.backButton.setOnClickListener {
            finish()
        }

        setupListParticipant()
        getListParticipant()
    }

    private fun setupListParticipant() {
        val lm = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        b.recyclerListParticipant.layoutManager = lm
        participantAdapter = ListParticipantAdapter(this, false)
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
}