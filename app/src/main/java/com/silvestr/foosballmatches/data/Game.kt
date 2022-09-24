package com.silvestr.foosballmatches.data

import java.util.*


data class Game(
    val date: Date,
    val player1: Player,
    val player2: Player,
    val score1: Int,
    val score2: Int
)
