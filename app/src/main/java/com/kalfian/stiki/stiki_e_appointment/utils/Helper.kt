package com.kalfian.stiki.stiki_e_appointment.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Helper {
    companion object {
        fun stringToBoolean(string: String): Boolean {
            return when (string) {
                "true", "1" -> true
                else -> false
            }
        }

        fun timestampToDate(timestamp: Long): String {
            val date = Date(timestamp * 1000)
            val sdf = SimpleDateFormat("dd MMMM yyyy HH:mm:ss", Locale.getDefault())
            return sdf.format(date)
        }
    }
}