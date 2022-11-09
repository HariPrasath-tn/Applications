

package com.example.retrofit.network

import com.example.retrofit.database.AnimeCharacter
import com.squareup.moshi.Json

/**
 * AnimeCharacter
 */
data class AnimeCharacter (
    @Json(name = "name")
    var name: String,
    @Json(name="image")
    val image: String
)

data class CharacterResponse(
    @Json(name="results")
    val result: List<AnimeCharacter>
)


fun CharacterResponse.asDatabaseModel(): Array<AnimeCharacter> {
    return result.map {
        com.example.retrofit.database.AnimeCharacter(
            name = it.name,
            image = it.image
        )
    }.toTypedArray()
}
