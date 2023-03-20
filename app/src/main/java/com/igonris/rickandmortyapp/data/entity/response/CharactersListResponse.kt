package com.igonris.rickandmortyapp.data.entity.response

data class CharactersListResponse(
    val info: Info,
    val results: List<SingleCharacterResponse>
)