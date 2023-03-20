package com.igonris.rickandmortyapp.domain

import com.igonris.rickandmortyapp.data.entity.ListCharacterInfo
import com.igonris.rickandmortyapp.data.repository.RickAndMortyAPI
import com.igonris.rickandmortyapp.utils.Event
import com.igonris.rickandmortyapp.utils.runAndProcess
import javax.inject.Inject

class GetCharactersListUseCase @Inject constructor(
    private val rickAndMortyAPI: RickAndMortyAPI
) {

    suspend fun execute(page: Int): Event<ListCharacterInfo> {
        return runAndProcess(
            action = {
                rickAndMortyAPI.getCharacterList(page = page)
            },
            parse = { result ->
                var nextPage: Int? = null
                if(!result.info.next.isNullOrEmpty() && result.info.next.contains("=")) {
                    nextPage = result.info.next.split("=")[1].toIntOrNull()
                }

                ListCharacterInfo(
                    nextPage = nextPage,
                    list = result.results.map{ it.toSingleCharacter() }
                )
            }
        )
    }
}