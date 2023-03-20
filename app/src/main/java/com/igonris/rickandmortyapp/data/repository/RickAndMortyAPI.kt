package com.igonris.rickandmortyapp.data.repository

import com.igonris.rickandmortyapp.data.entity.response.CharactersListResponse
import com.igonris.rickandmortyapp.data.entity.response.SingleCharacterResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyAPI {

    @GET("character/{id}")
    suspend fun getCharacterDetails(
        @Path("id") idCharacter: String
    ): SingleCharacterResponse

    @GET("character")
    suspend fun getCharacterList(
        @Query("page") page: Int,
        @Query("name") name: String,
        @Query("status") status: String,
        @Query("species") species: String,
        @Query("type") type: String,
        @Query("gender") gender: String,
    ): CharactersListResponse
}