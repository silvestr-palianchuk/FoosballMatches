package com.silvestr.foosballmatches.utils

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


object DateHelper {
    private const val DATE_FORMAT = "d MMM yyyy"
    private val simpleDateFormat = SimpleDateFormat(DATE_FORMAT, Locale.US)

    fun getFormattedDate(timestamp: Long): String {
        val date = Date(timestamp)
        return simpleDateFormat.format(date)
    }

    fun convertStringDateToLong(date: String): Long {
        var timestamp = Date().time
        try {
            timestamp = simpleDateFormat.parse(date).time
        } catch (e: ParseException) {
            Log.e("DateHelper", "Error: Unparseable date: $date")
        }
        return timestamp
    }

    fun showDatePickerDialog(
        context: Context,
        datePickerDialogListener: DatePickerDialog.OnDateSetListener
    ) {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            context, datePickerDialogListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.maxDate = calendar.timeInMillis
        datePickerDialog.show()
    }

}