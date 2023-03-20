package com.igonris.rickandmortyapp.data.entity.response

import com.igonris.rickandmortyapp.data.entity.Character
import com.igonris.rickandmortyapp.data.entity.SimpleCharacter

data class SingleCharacterResponse(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
) {
    fun toSingleCharacter(): SimpleCharacter {
        return SimpleCharacter(id = id, name = name, image = image, gender = gender, type = type)
    }

    fun toCharacter(): Character {
        return Character(id = id, name = name, image = image, details = origin.name)
    }
}