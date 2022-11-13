package com.example.retrofit.repository

import androidx.lifecycle.LiveData
import com.example.retrofit.database.AnimeCharacter
import com.example.retrofit.database.CharacterDatabase
import com.example.retrofit.network.ApiClient
import com.example.retrofit.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await

/**
 * CharacterRepository is an user defined repository class (plain class) that decides when to fetch
 * data from which data source and when to refresh the offline cache. In this way data process logic
 * is hidden from the viewModel and we can keep small and maintainable. This repository class helps
 * us to keep app layers separate from one another
 */
class CharacterRepository (private val database: CharacterDatabase) {

    /**
     * characters is a liveData variable that holds a list of dataclass AnimeCharacter objects
     *
     * Value Assigning methodology:
     *      -> database.characterDAO.getAllCharacter fetches all the character from the database with
     *      respect to the query given.
     *      -> Then each records are mapped to the object of the of the
     *      data class AnimeCharacter, then assigned to the variable
     *
     */
    val characters: LiveData<List<AnimeCharacter>> = database.characterDAO.getAllCharacter()


    /**
     * refreshCharacter() is a method that runs in separate thread (thread other than main) that
     * update the database
     *
     *
     * it update all the values in the database
     *      -> insert if the data doesn't exist already
     *      -> replace if the data already exists
     *
     */
    suspend fun refreshCharacter() {
        withContext(Dispatchers.IO) {
            try {
                var page = 1
                while (true) {
                    val characters = ApiClient.apiService.fetchCharacters("${page++}").await().results
                    print("\n\n\n$characters")
                    database.characterDAO.insertAll(*characters.asDatabaseModel())
                }
            }catch (ignored:Exception){
                println("\n\n\n\n\n${ignored.message}")
            }
        }
    }
}