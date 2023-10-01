package com.kalfian.stiki.stiki_e_appointment.modules.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.kalfian.stiki.stiki_e_appointment.adapters.ListChatAdapter
import com.kalfian.stiki.stiki_e_appointment.adapters.ListParticipantAdapter
import com.kalfian.stiki.stiki_e_appointment.databinding.ActivityChatBinding
import com.kalfian.stiki.stiki_e_appointment.databinding.ActivityCreateAppointmentStudentBinding
import com.kalfian.stiki.stiki_e_appointment.models.Chat
import com.kalfian.stiki.stiki_e_appointment.models.Participant
import com.kalfian.stiki.stiki_e_appointment.utils.Constant
import com.kalfian.stiki.stiki_e_appointment.utils.Helper
import com.kalfian.stiki.stiki_e_appointment.utils.SharedPreferenceUtil

class ChatActivity : AppCompatActivity(), ListChatAdapter.AdapterListChatOnClickListener {

    private lateinit var b: ActivityChatBinding
    private var isLecture: Boolean = false

    private lateinit var chatAdapter: ListChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityChatBinding.inflate(layoutInflater)
        val v = b.root
        setContentView(v)

        isLecture = Helper.stringToBoolean(SharedPreferenceUtil.retrieve(applicationContext, Constant.SHARED_IS_LECTURE, "false"))

        b.nav.headerTitle.text = "Bimbingan Bab 1"
        b.nav.backButton.setOnClickListener {
            finish()
        }

        setupListChat()
        getListChat()
    }

    private fun setupListChat() {
        val lm = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        b.recyclerChat.layoutManager = lm
        chatAdapter = ListChatAdapter(this)
        b.recyclerChat.adapter = chatAdapter
    }

    private fun getListChat() {
        chatAdapter.clear()
        chatAdapter.add(Chat("1", "00:00", "Kukuh Alfian", "Testing", false))
        chatAdapter.add(Chat("1", "00:00", "Dosen", "Testing", true))
        chatAdapter.add(Chat("1", "00:00", "Kukuh Alfian", "Testing", false))
        chatAdapter.add(Chat("1", "00:00", "Dosen", "Testing", true))
        chatAdapter.add(Chat("1", "00:00", "Kukuh Alfian", "Testing", false))
        chatAdapter.add(Chat("1", "00:00", "Dosen", "Testing", true))
        chatAdapter.add(Chat("1", "00:00", "Kukuh Alfian", "Testing", false))
        chatAdapter.add(Chat("1", "00:00", "Dosen", "Testing", true))
        chatAdapter.add(Chat("1", "00:00", "Kukuh Alfian", "Testing", false))
        chatAdapter.add(Chat("1", "00:00", "Dosen", "Testing", true))
    }

    override fun onItemClickListener(data: Chat) {

    }
}