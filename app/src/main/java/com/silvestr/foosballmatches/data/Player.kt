package com.silvestr.foosballmatches.data


data class Player(
    val firstName: String,
    val lastName: String
) {
    val fullName = "$firstName $lastName"
}
