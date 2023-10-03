package com.kalfian.stiki.stiki_e_appointment.utils

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app

object RealtimeDB {
    private const val dbPath = "https://stiki-e-appointment-default-rtdb.asia-southeast1.firebasedatabase.app/"
    private val dbRef = Firebase.database(dbPath).reference

    fun reference() = dbRef
}