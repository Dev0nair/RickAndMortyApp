package com.igonris.rickandmortyapp.presentation.characterdetails

import com.igonris.rickandmortyapp.data.entity.Character

data class CharacterDetailsState(
    val loading: Boolean = false,
    val character: Character? = null,
    val error: String? = null
)
