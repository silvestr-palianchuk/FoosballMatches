package com.silvestr.foosballmatches.data

import android.text.format.DateFormat
import java.util.*


data class Game(
    val id: Int,
    val date: Long,
    val player1: Player,
    val player2: Player,
    val score1: Int,
    val score2: Int
) {
    fun getFormattedDate(): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = date
        return DateFormat.format("dd/MM/yy", calendar).toString()
    }
}

