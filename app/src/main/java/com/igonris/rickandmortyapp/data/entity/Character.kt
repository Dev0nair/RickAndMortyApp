package com.igonris.rickandmortyapp.data.entity

data class Character(
    val id: Int,
    val name: String,
    val image: String,
    val gender: String,
    val origin: String = "",
    val status: String = "",
    val type: String = ""
)
