package com.igonris.rickandmortyapp.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SimpleCharacter(
    val id: Int,
    val name: String,
    val gender: String,
    val type: String,
    val image: String
): Parcelable