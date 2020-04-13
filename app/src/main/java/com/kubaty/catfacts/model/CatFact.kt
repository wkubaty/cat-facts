package com.kubaty.catfacts.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class CatFact(
    val _id: String,
    val updatedAt: Date,
    val text: String,
    var drawableId: Int
) : Parcelable