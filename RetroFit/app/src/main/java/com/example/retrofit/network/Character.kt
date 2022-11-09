package com.example.retrofit.network

import com.squareup.moshi.Json
data class Character (
    @Json(name = "name")
    var name: String,
    @Json(name="image")
    val image: String
)

data class CharacterResponse(
    @Json(name="results")
    val result: List<Character>
)