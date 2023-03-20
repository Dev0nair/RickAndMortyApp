package com.igonris.rickandmortyapp.presentation.home

import android.os.Parcelable
import com.igonris.rickandmortyapp.data.entity.SimpleCharacter
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HomeViewState(
    val loading: Boolean = false,
    val data: List<SimpleCharacter> = emptyList(),
    val error: String? = null,
    val showDialog: Boolean = false
): Parcelable
