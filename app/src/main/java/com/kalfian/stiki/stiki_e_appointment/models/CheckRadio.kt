package com.kalfian.stiki.stiki_e_appointment.models

data class CheckRadio(
    val text: String,
    var isChecked: Boolean = false,
    val id: Int = 0
)