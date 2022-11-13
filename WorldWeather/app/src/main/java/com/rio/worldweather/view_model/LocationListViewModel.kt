package com.rio.worldweather.view_model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rio.worldweather.model.database.Database
import com.rio.worldweather.model.database.DatabaseLocation
import com.rio.worldweather.model.repository.WeatherWorldRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LocationListViewModel(val context:Context) :  ViewModel(){
    lateinit var staredLocationList:LiveData<List<DatabaseLocation>>
    val database = Database.getInstance(context)
    private val weatherWorldRepository = WeatherWorldRepository.getInstance(this, database)
    private val job = Job()
    private val uiScope = CoroutineScope(job)

    init{
        // launches the coroutine to fetch the data from the database
        uiScope.launch {
            staredLocationList = weatherWorldRepository.databaseLocationList
        }
    }

    /**
     * [deleteStaredLocation] is the method that launches a coroutine
     * to remove the provided city from the database
     *
     * @param location is of type [DatabaseLocation] representing the location that is to be removed
     * from the database
     */
    fun deleteStaredLocation(location: DatabaseLocation){
        uiScope.launch {
            weatherWorldRepository.deleteThis(location.cityName)
        }
    }

}