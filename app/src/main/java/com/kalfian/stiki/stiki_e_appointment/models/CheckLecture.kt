package com.kalfian.stiki.stiki_e_appointment.models

data class CheckLecture(
    val text: String,
    var isChecked: Boolean = false,
    val id: Int = 0
)
