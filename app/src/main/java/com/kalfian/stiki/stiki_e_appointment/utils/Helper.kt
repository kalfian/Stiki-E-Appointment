package com.kalfian.stiki.stiki_e_appointment.utils

class Helper {
    companion object {
        fun stringToBoolean(string: String): Boolean {
            return when (string) {
                "true", "1" -> true
                else -> false
            }
        }
    }
}