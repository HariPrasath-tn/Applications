package com.example.retrofit.model_view

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofit.database.AnimeCharacter
import com.example.retrofit.database.CharacterDatabase.Companion.getINSTANCE
import com.example.retrofit.repository.CharacterRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * HomeFragmentViewModel is a user defined class that extends lifecycle ViewModel class
 *  > has two variables database, characterRepository, to store the instance of the corresponding
 *      classes
 *  > init is a secondary constructor that launch the suspend function refreshCharacter()
 */
class HomeFragmentViewModel(application: Application) : ViewModel() {
    private val database = getINSTANCE(application)
    private val characterRepository = CharacterRepository(database)

    init {
        viewModelScope.launch {
            characterRepository.refreshCharacter()
        }
    }

    val characterList:LiveData<List<AnimeCharacter>> = characterRepository.characters
}