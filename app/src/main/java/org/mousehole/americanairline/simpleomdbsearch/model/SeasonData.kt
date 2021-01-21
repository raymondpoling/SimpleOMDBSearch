package org.mousehole.americanairline.simpleomdbsearch.model

import android.os.Parcel
import android.os.Parcelable

data class SeasonData(val series: String, val season: String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()?:"",
        parcel.readString()?:""
    )

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<SeasonData> {
        override fun createFromParcel(parcel: Parcel): SeasonData {
            return SeasonData(parcel)
        }

        override fun newArray(size: Int): Array<SeasonData?> {
            return arrayOfNulls(size)
        }
    }
}