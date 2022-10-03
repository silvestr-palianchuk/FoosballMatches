package com.silvestr.foosballmatches.utils

import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


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
}