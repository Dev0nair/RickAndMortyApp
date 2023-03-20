package com.igonris.rickandmortyapp.domain

import com.igonris.rickandmortyapp.data.entity.Character
import com.igonris.rickandmortyapp.data.repository.RickAndMortyAPI
import com.igonris.rickandmortyapp.utils.Event
import com.igonris.rickandmortyapp.utils.runAndProcess
import javax.inject.Inject


interface IGetCharacterDetailsUseCase {
    suspend operator fun invoke(idCharacter: String): Event<Character>
}

class GetCharacterDetailsUseCase @Inject constructor(
    private val rickAndMortyAPI: RickAndMortyAPI
): IGetCharacterDetailsUseCase {

    override suspend operator fun invoke(idCharacter: String): Event<Character> {
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