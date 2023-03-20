package com.igonris.rickandmortyapp.di

import com.igonris.rickandmortyapp.data.repository.RickAndMortyAPI
import com.igonris.rickandmortyapp.domain.GetCharacterDetailsUseCase
import com.igonris.rickandmortyapp.domain.GetCharactersListUseCase
import com.igonris.rickandmortyapp.domain.IGetCharacterDetailsUseCase
import com.igonris.rickandmortyapp.domain.IGetCharactersListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module(includes = [RepositoryModule::class])
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetFilterCharactersList(rickAndMortyAPI: RickAndMortyAPI): IGetCharactersListUseCase {
        return GetCharactersListUseCase(rickAndMortyAPI = rickAndMortyAPI)
    }

    @Provides
    fun provideGetCharacterDetails(rickAndMortyAPI: RickAndMortyAPI): IGetCharacterDetailsUseCase {
        return GetCharacterDetailsUseCase(rickAndMortyAPI = rickAndMortyAPI)
    }
}