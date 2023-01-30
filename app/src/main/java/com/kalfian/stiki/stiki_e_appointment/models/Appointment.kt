package com.kalfian.stiki.stiki_e_appointment.models

import java.io.Serializable

data class Appointment (
    var id: String = "",
    var image: String = "",
    var title: String = "",
    var startDate: String = "",
    var endDate: String = "",
    var participant: Participant? = Participant()
): Serializable