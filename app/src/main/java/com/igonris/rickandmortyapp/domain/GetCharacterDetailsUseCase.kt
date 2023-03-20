package com.igonris.rickandmortyapp.domain

import com.igonris.rickandmortyapp.data.entity.filter.ApiCharacterFilter
import com.igonris.rickandmortyapp.data.entity.Character
import com.igonris.rickandmortyapp.data.repository.RickAndMortyAPI
import com.igonris.rickandmortyapp.utils.Event
import com.igonris.rickandmortyapp.utils.runAndProcess
import javax.inject.Inject

class GetCharacterDetailsUseCase @Inject constructor(
    private val rickAndMortyAPI: RickAndMortyAPI
) {

    suspend fun execute(idCharacter: String): Event<Character> {
        return runAndProcess(
            action = {
                rickAndMortyAPI.getCharacterDetails(idCharacter)
            },
            parse = { result ->
                result.toCharacter()
            }
        )
    }
}