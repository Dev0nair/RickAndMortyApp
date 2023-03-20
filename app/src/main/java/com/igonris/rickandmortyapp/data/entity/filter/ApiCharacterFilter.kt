package com.igonris.rickandmortyapp.data.entity.filter

data class ApiCharacterFilter(
    val name: String = "",
    val status: Status = Status.none,
    val species: String = "",
    val type: String = "",
    val gender: Gender = Gender.none,
    val actualPage: Int = 0
)