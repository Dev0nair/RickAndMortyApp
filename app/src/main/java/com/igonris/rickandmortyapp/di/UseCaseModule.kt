package com.igonris.rickandmortyapp.di

import com.igonris.rickandmortyapp.data.repository.RickAndMortyAPI
import com.igonris.rickandmortyapp.domain.GetCharacterDetailsUseCase
import com.igonris.rickandmortyapp.domain.GetCharactersListUseCase
import com.igonris.rickandmortyapp.domain.GetFilteredCharactersListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module(includes = [RepositoryModule::class])
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetCharactersList(rickAndMortyAPI: RickAndMortyAPI): GetCharactersListUseCase {
        return GetCharactersListUseCase(rickAndMortyAPI = rickAndMortyAPI)
    }

    @Provides
    fun provideGetFilterCharactersList(rickAndMortyAPI: RickAndMortyAPI): GetFilteredCharactersListUseCase {
        return GetFilteredCharactersListUseCase(rickAndMortyAPI = rickAndMortyAPI)
    }

    @Provides
    fun provideGetCharacterDetails(rickAndMortyAPI: RickAndMortyAPI): GetCharacterDetailsUseCase {
        return GetCharacterDetailsUseCase(rickAndMortyAPI = rickAndMortyAPI)
    }
}