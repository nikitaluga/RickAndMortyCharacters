package com.rickandmortycharacters.data

import com.rickandmortycharacters.data.model.CharacterData
import com.rickandmortycharacters.data.model.EpisodeResponse
import com.rickandmortycharacters.data.model.LocationResponse
import com.rickandmortycharacters.data.model.CharactersResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestService {

    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int
    ): CharactersResponse

    @GET("character/{id}")
    suspend fun getCharacter(
        @Path("id") id: Int
    ): CharacterData

    @GET("episode/{id}")
    suspend fun getEpisode(
        @Path("id") id: Int
    ): EpisodeResponse

    @GET("location/{id}")
    suspend fun getLocation(
        @Path("id") id: Int
    ): LocationResponse
}