package com.rickandmortycharacters.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class CharacterData(
    @Json(name = "id")
    val id: Int, // 361
    @Json(name = "name")
    val name: String, // Toxic Rick
    @Json(name = "status")
    val status: String, // Dead
    @Json(name = "species")
    val species: String, // Humanoid
    val type: String, // Rick's Toxic Sid
    @Json(name = "gender")
    val gender: String, // Male
    @Json(name = "origin")
    val origin: Origin,
    @Json(name = "location")
    val location: Location,
    @Json(name = "image")
    val image: String, // https://rickandmortyapi.com/api/character/avatar/361.jpeg
    @Json(name = "episode")
    val episode: List<String>, // https://rickandmortyapi.com/api/episode/27
    val url: String, // https://rickandmortyapi.com/api/character/361
    val created: String, // https://rickandmortyapi.com/api/character/361
): Parcelable {

    @Parcelize
    @JsonClass(generateAdapter = true)
    data class Origin(
        @Json(name = "name")
        val name: String, // Alien Sp
        val url: String // https://rickandmortyapi.com/api/location/6
    ): Parcelable

    @Parcelize
    @JsonClass(generateAdapter = true)
    data class Location(
        @Json(name = "name")
        val name: String, // Earth
        @Json(name = "url")
        val url: String // "https://rickandmortyapi.com/api/location/20
    ): Parcelable
}
