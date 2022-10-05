package com.silvestr.foosballmatches.data

import android.os.Parcel
import android.os.Parcelable

data class Ranking(
    val player: Player?,
    val played: Int,
    val won: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Player::class.java.classLoader),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(player, flags)
        parcel.writeInt(played)
        parcel.writeInt(won)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Ranking> {
        override fun createFromParcel(parcel: Parcel): Ranking {
            return Ranking(parcel)
        }

        override fun newArray(size: Int): Array<Ranking?> {
            return arrayOfNulls(size)
        }
    }
}