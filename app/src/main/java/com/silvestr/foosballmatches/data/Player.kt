package com.silvestr.foosballmatches.data

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Player(
    @PrimaryKey
    val id: Int,
    val firstName: String?,
    val lastName: String?
) : Parcelable {
    @Ignore
    val fullName = "$firstName $lastName"

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(firstName)
        parcel.writeString(lastName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Player> {
        override fun createFromParcel(parcel: Parcel): Player {
            return Player(parcel)
        }

        override fun newArray(size: Int): Array<Player?> {
            return arrayOfNulls(size)
        }
    }
}
