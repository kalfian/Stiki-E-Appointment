package com.kalfian.stiki.stiki_e_appointment.models

import java.io.Serializable

data class Participant (
    var id: String = "",
    var name: String = "",
    var identity: String = ""
): Serializable