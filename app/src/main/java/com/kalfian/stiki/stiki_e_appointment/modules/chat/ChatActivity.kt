package com.kalfian.stiki.stiki_e_appointment.modules.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kalfian.stiki.stiki_e_appointment.adapters.ListChatAdapter
import com.kalfian.stiki.stiki_e_appointment.databinding.ActivityChatBinding
import com.kalfian.stiki.stiki_e_appointment.models.Chat
import com.kalfian.stiki.stiki_e_appointment.models.User
import com.kalfian.stiki.stiki_e_appointment.models.appointmentResponse.GetAppointmentDetailResponse
import com.kalfian.stiki.stiki_e_appointment.utils.Alert
import com.kalfian.stiki.stiki_e_appointment.utils.Constant
import com.kalfian.stiki.stiki_e_appointment.utils.Helper
import com.kalfian.stiki.stiki_e_appointment.utils.RealtimeDB
import com.kalfian.stiki.stiki_e_appointment.utils.RetrofitClient
import com.kalfian.stiki.stiki_e_appointment.utils.SharedPreferenceUtil

class ChatActivity : AppCompatActivity(), ListChatAdapter.AdapterListChatOnClickListener {

    private lateinit var b: ActivityChatBinding
    private var isLecture: Boolean = false
    private var title: String = ""
    private var appointmentId: Int = 0

    private var users = ArrayList<User>()

    private lateinit var chatAdapter: ListChatAdapter
    private lateinit var db: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityChatBinding.inflate(layoutInflater)

        val v = b.root

        title = intent.getStringExtra(Constant.INTENT_CHAT_TITLE) ?: ""
        appointmentId = intent.getIntExtra(Constant.INTENT_APPOINTMENT_ID, 0)
        isLecture = Helper.stringToBoolean(SharedPreferenceUtil.retrieve(applicationContext, Constant.SHARED_IS_LECTURE, "false"))

        setContentView(v)

        setupPage {
            db = RealtimeDB.reference().child("chatroom").child(appointmentId.toString()).child("message")
            val messageRef = db


            messageRef.addChildEventListener(object : ChildEventListener{
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val chat = snapshot.getValue(Chat::class.java)
                    if (chat != null) {

                        Log.d("ChattingGaes", users.toString())

                        chat.participantName = users.find { it.id == chat.participantId }?.name ?: chat.participantId.toString()

                        chatAdapter.add(chat)
                        b.nestedScrollView.post {
                            b.nestedScrollView.fullScroll(View.FOCUS_DOWN)
                        }
                    }
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

                }

                override fun onChildRemoved(snapshot: DataSnapshot) {

                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        }

        b.btnSend.setOnClickListener {
            val message = b.textInput.text.toString()
            val timestampNow = System.currentTimeMillis() / 1000
            val id = SharedPreferenceUtil.retrieve(applicationContext, Constant.SHARED_ID, "0")
            val isLecture = Helper.stringToBoolean(SharedPreferenceUtil.retrieve(applicationContext, Constant.SHARED_IS_LECTURE, "false"))

            if (message.isNotEmpty()) {
                val messageId = db.push().key
                if (messageId != null) {

                    val chat = Chat(
                        messageId,
                        id.toInt(),
                        "",
                        timestampNow,
                        message,
                        isLecture
                    )

                    db.child(messageId).setValue(chat).addOnSuccessListener {
                        b.textInput.text.clear()
                    }.addOnFailureListener { e ->
                        Alert.showError(this, "Error", e.message ?: "Error")
                    }
                }
            }
        }
    }

    private fun setupPage(callback: () -> Unit = {}){
        if (appointmentId == 0) {
            finish()
        }

        b.nav.headerTitle.text = title
        b.nav.backButton.setOnClickListener {
            finish()
        }

        b.nestedScrollView.post {
            b.nestedScrollView.fullScroll(View.FOCUS_DOWN)
        }

        setupListChat()
        getListChat()

        if(isLecture) {
            setupPageLecture(callback)
        } else {
            setupPageStudent(callback)
        }
    }

    private fun setupListChat() {
        val lm = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        b.recyclerChat.layoutManager = lm
        chatAdapter = ListChatAdapter(this)
        b.recyclerChat.adapter = chatAdapter
    }

    private fun getListChat() {

    }

    override fun onItemClickListener(data: Chat) {

    }

    private fun setupPageStudent(callback: () -> Unit) {
        RetrofitClient.callAuth(applicationContext).getStudentAppointmentDetail(appointmentId,
            loadStudents = true,
            loadLectures = true
        ).enqueue(object : retrofit2.Callback<GetAppointmentDetailResponse> {
            override fun onResponse(call: retrofit2.Call<GetAppointmentDetailResponse>, response: retrofit2.Response<GetAppointmentDetailResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    if (data == null) {
                        Alert.showError(
                            this@ChatActivity,
                            "Gagal mendapatkan Chat Bimbingan!", "coba lagi"
                        )
                        finish()
                        return
                    }

                    users.add(data.participants.student)
                    users.add(data.participants.lecture)
                    users.add(data.participants.lecture2 ?: User())
                    callback()
                } else {
                    finish()
                }
            }

            override fun onFailure(call: retrofit2.Call<GetAppointmentDetailResponse>, t: Throwable) {
                finish()
            }
        })
    }

    private fun setupPageLecture(callback: () -> Unit) {
        RetrofitClient.callAuth(applicationContext).getLectureAppointmentDetail(appointmentId,
            loadStudents = true,
            loadLectures = true
        ).enqueue(object : retrofit2.Callback<GetAppointmentDetailResponse> {
            override fun onResponse(call: retrofit2.Call<GetAppointmentDetailResponse>, response: retrofit2.Response<GetAppointmentDetailResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    if (data == null) {
                        Alert.showError(
                            this@ChatActivity,
                            "Gagal mendapatkan Chat Bimbingan!", "coba lagi"
                        )
                        finish()
                        return
                    }

                    users.add(data.participants.student)
                    users.add(data.participants.lecture)
                    users.add(data.participants.lecture2 ?: User())

                    callback()

                } else {
                    finish()
                }
            }

            override fun onFailure(call: retrofit2.Call<GetAppointmentDetailResponse>, t: Throwable) {
                finish()
            }
        })
    }
}