package com.kalfian.stiki.stiki_e_appointment.utils

import android.app.DatePickerDialog
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

fun EditText.showDatePickerDialog(activity: AppCompatActivity, initialDate: String? = null, callback: (date: String, dateDB: String) -> Unit) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(activity, { _, selectedYear, selectedMonth, selectedDay ->

        calendar.set(Calendar.YEAR, selectedYear)
        calendar.set(Calendar.MONTH, selectedMonth)
        calendar.set(Calendar.DAY_OF_MONTH, selectedDay)


        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dateFormatShowed = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

        val selectedDateDB = dateFormat.format(calendar.time)
        val selectedDate = dateFormatShowed.format(calendar.time)

        callback(selectedDate, selectedDateDB) }, year, month, dayOfMonth
    )

    if (initialDate != null) {
        val dateParts = initialDate.split("-")
        if (dateParts.size == 3) {
            val initialYear = dateParts[0].toInt()
            val initialMonth = dateParts[1].toInt() - 1
            val initialDay = dateParts[2].toInt()
            datePickerDialog.updateDate(initialYear, initialMonth, initialDay)
        }
    }

    this.setOnClickListener {
        datePickerDialog.show()
    }
}