package com.igonris.rickandmortyapp.data.entity.response

import com.igonris.rickandmortyapp.data.entity.Character
import com.igonris.rickandmortyapp.data.entity.SimpleCharacter

data class SingleCharacterResponse(
    val created: String = "",
    val episode: List<String> = emptyList(),
    val gender: String = "",
    val id: Int = -1,
    val image: String = "",
    val location: Location = Location("", ""),
    val name: String = "",
    val origin: Origin = Origin("", ""),
    val species: String = "",
    val status: String = "",
    val type: String = "",
    val url: String = ""
) {
    fun toSingleCharacter(): SimpleCharacter {
        return SimpleCharacter(id = id, name = name, image = image, gender = gender, type = type)
    }

    fun toCharacter(): Character {
        return Character(id = id, name = name, image = image, origin = origin.name, gender = gender, type = type, status = status)
    }
}