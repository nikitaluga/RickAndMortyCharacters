package com.rickandmortycharacters.data

import com.rickandmortycharacters.data.model.CharacterData
import com.rickandmortycharacters.data.model.EpisodeResponse
import com.rickandmortycharacters.data.model.LocationResponse
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val rawApi: RestService
) : CharactersRepository {

    override suspend fun getCharacters(page: Int): List<CharacterData> =
        rawApi.getCharacters(page).results

    override suspend fun getCharacter(id: Int): CharacterData = rawApi.getCharacter(id)

    override suspend fun getEpisode(id: Int): EpisodeResponse = rawApi.getEpisode(id)

    override suspend fun getLocation(id: Int): LocationResponse = rawApi.getLocation(id)
}