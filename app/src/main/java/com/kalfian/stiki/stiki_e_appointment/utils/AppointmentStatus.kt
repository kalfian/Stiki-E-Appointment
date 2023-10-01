package com.kalfian.stiki.stiki_e_appointment.utils

import android.content.Context
import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import com.kalfian.stiki.stiki_e_appointment.R

class AppointmentStatus {
    companion object {
        const val PENDING = 200
        const val ACCEPTED = 201
        const val REJECTED = 202
        const val CANCELED = 203
        const val DONE = 2004
    }

    fun getStatusText(status: Int): String {
        return when (status) {
            PENDING -> "Menunggu Persetujuan"
            ACCEPTED -> "Disetujui"
            REJECTED -> "Ditolak"
            CANCELED -> "Dibatalkan"
            DONE -> "Selesai"
            else -> "-"
        }
    }


    fun getStatusColor(status: Int, context: Context): ColorStateList {
        return when (status) {
            PENDING -> ColorStateList.valueOf(ContextCompat.getColor(context, R.color.gray))
            ACCEPTED -> ColorStateList.valueOf(ContextCompat.getColor(context, R.color.green))
            REJECTED -> ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red))
            CANCELED -> ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red))
            DONE -> ColorStateList.valueOf(ContextCompat.getColor(context, R.color.blue))
            else -> ColorStateList.valueOf(ContextCompat.getColor(context, R.color.gray))
        }
    }
}