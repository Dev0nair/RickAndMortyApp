package com.igonris.rickandmortyapp.presentation.home

import com.igonris.rickandmortyapp.data.entity.SimpleCharacter

data class HomeViewState(
    val loading: Boolean = false,
    val data: List<SimpleCharacter> = emptyList(),
    val error: String? = null,
)
