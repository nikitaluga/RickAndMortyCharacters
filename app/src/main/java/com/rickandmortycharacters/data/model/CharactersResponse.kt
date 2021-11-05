package com.rickandmortycharacters.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class CharactersResponse(
    @Json(name = "info")
    val info: Info,
    @Json(name = "results")
    val results: List<CharacterData>
) {
    @JsonClass(generateAdapter = true)
    data class Info(
        val count: Int, // 671
        val pages: Int, // 34
        val next: String?, // https://rickandmortyapi.com/api/character/?page=20
        val prev: String? // https://rickandmortyapi.com/api/character/?page=18
    )
}