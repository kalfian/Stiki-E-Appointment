import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.EditText
import java.text.SimpleDateFormat
import java.util.*

fun EditText.setupDateTimePicker(context: Context, callback: (selected: String, selectedDB: String) -> Unit) {
    val calendar = Calendar.getInstance()

    this.setOnClickListener {
        showDateTimePicker(context, calendar, callback)
    }
}

private fun EditText.showDateTimePicker(context: Context, calendar: Calendar, callback: (selected: String, selectedDB: String) -> Unit) {

    // Create a DatePickerDialog
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            // Handle date selection
            calendar.set(year, month, dayOfMonth)
            showTimePicker(context, calendar, callback)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    // Show the date picker dialog
    datePickerDialog.show()
}

private fun EditText.showTimePicker(context: Context, calendar: Calendar, callback: (selected: String, selectedDB: String) -> Unit) {
    val initialHour = calendar.get(Calendar.HOUR_OF_DAY)
    val initialMinute = calendar.get(Calendar.MINUTE)

    // Create a TimePickerDialog
    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            // Handle time selection
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)

            // Format the selected datetime (optional)
            val selectedDateTimeDB = SimpleDateFormat("yyyy-MM-dd HH:mm:00", Locale.getDefault()).format(calendar.time)
            val selectedDateTimeView = SimpleDateFormat("dd MMMM yyyy HH:mm", Locale.getDefault()).format(calendar.time)
            // Call the callback with the selected datetime
            callback(selectedDateTimeView, selectedDateTimeDB)
        },
        initialHour,
        initialMinute,
        true // 24-hour format
    )

    // Show the time picker dialog
    timePickerDialog.show()
}