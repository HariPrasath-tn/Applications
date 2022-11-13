package com.example.retrofit.network

import com.example.retrofit.database.AnimeCharacter


/**
 * [asDatabaseModel] is an extension function for the interface List with generics type Result
 * @return Array<AnimeCharacters>
 */
fun List<com.example.retrofit.network.data_class.Result>.asDatabaseModel(): Array<AnimeCharacter> {
    // mapping the network response with the AnimeCharacter Data class
    return map {
        AnimeCharacter(
            id = it.id.toString(),
            name = it.name ?: "not Available",
            image = it.image ?: "not Available",
            aliveStatus = it.status ?: "not Available",
            location = it.location?.name ?: "not Available",
            species = it.species ?: "not Available",
            origin = it.origin?.name ?: "not Available",
            gender = it.gender ?: "not Available"
        )
    }.toTypedArray()
}
