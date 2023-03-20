package com.igonris.rickandmortyapp.domain

import android.net.Uri
import com.igonris.rickandmortyapp.data.entity.ListCharacterInfo
import com.igonris.rickandmortyapp.data.entity.filter.ApiCharacterFilter
import com.igonris.rickandmortyapp.data.repository.RickAndMortyAPI
import com.igonris.rickandmortyapp.utils.Event
import com.igonris.rickandmortyapp.utils.runAndProcess
import javax.inject.Inject

interface IGetCharactersListUseCase {
    suspend operator fun invoke(page: Int, filter: ApiCharacterFilter): Event<ListCharacterInfo>
}

class GetCharactersListUseCase @Inject constructor(
    private val rickAndMortyAPI: RickAndMortyAPI
): IGetCharactersListUseCase {

    override suspend operator fun invoke(page: Int, filter: ApiCharacterFilter): Event<ListCharacterInfo> {
        return runAndProcess(
            action = {
                rickAndMortyAPI.getCharacterList(
                    page = page,
                    name = filter.name,
                    status = filter.status.parseName(),
                    species = filter.species,
                    type = filter.type,
                    gender = filter.gender.parseName()
                )
            },
            parse = { result ->
                val uriUrlNextPage: Uri = Uri.parse(result.info.next.orEmpty())
                val nextPage: Int? = uriUrlNextPage.getQueryParameter("page")?.toIntOrNull()

                ListCharacterInfo(
                    nextPage = nextPage,
                    list = result.results.map{ it.toSingleCharacter() }
                )
            }
        )
    }
}