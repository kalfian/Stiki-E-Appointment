package com.kalfian.stiki.stiki_e_appointment.models

import java.io.Serializable

data class Chat (
    var id: String = "",
    var time: String = "",
    var title: String = "",
    var content: String = "",
    var isLecture: Boolean = false
): Serializable