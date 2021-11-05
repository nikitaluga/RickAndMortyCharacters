package com.rickandmortycharacters.data

import com.rickandmortycharacters.data.model.CharacterData
import com.rickandmortycharacters.data.model.EpisodeResponse
import com.rickandmortycharacters.data.model.LocationResponse

interface CharactersRepository {
    suspend fun getCharacters(page: Int = 1): List<CharacterData>
    suspend fun getCharacter(id: Int): CharacterData
    suspend fun getEpisode(id: Int): EpisodeResponse
    suspend fun getLocation(id: Int): LocationResponse
}