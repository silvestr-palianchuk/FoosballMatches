package com.silvestr.foosballmatches.data

import android.os.Parcel
import android.os.Parcelable
import android.text.format.DateFormat
import java.util.*


data class Game(
    val id: Int,
    val date: Long,
    val player1: Player?,
    val player2: Player?,
    val score1: Int,
    val score2: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readLong(),
        parcel.readParcelable(Player::class.java.classLoader),
        parcel.readParcelable(Player::class.java.classLoader),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeLong(date)
        parcel.writeParcelable(player1, flags)
        parcel.writeParcelable(player2, flags)
        parcel.writeInt(score1)
        parcel.writeInt(score2)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Game> {
        override fun createFromParcel(parcel: Parcel): Game {
            return Game(parcel)
        }

        override fun newArray(size: Int): Array<Game?> {
            return arrayOfNulls(size)
        }
    }

    fun getFormattedDate(): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = date
        return DateFormat.format("dd/MM/yy", calendar).toString()
    }
}

