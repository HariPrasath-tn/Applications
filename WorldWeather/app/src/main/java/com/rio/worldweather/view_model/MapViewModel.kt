package com.rio.worldweather.view_model

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.rio.worldweather.model.database.Database
import com.rio.worldweather.model.database.DatabaseLocation
import com.rio.worldweather.model.repository.WeatherWorldRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MapViewModel(val context:Context): ViewModel() {
    var job = Job()
    private var uiScope = CoroutineScope(job)
    var city = MutableLiveData<DatabaseLocation>()
    private var database = Database.getInstance(context)
    private var repository: WeatherWorldRepository = WeatherWorldRepository.getInstance(this, database)
    var staredCities = MutableLiveData<List<String>>()
    val staredCitiesLocation: LiveData<List<DatabaseLocation>> = repository.databaseLocationList

    init {
        fetchCitiesFromDB()
    }
    /**
     * [getCity] is the method that initializes the city search in api using given coordinates
     *
     * @param lat of type String representing latitude of the place
     * @param lon of type String representing longitude of the place
     * @return unit
     */
    fun getCity(lat:String, lon:String){
        uiScope.launch {
            var data = repository.fetchWeatherData(lat, lon).data[0]
            city.postValue(DatabaseLocation(data.city_name, data.lat.toString(), data.lon.toString()))

        }
    }

    /**
     * [fetchCitiesFromDB] is the method that initializes fetching cities name from database
     */
    fun fetchCitiesFromDB(){
        uiScope.launch {
            var temp = repository.fetchCities()
            staredCities.postValue(temp)
        }
    }

    /**
     * [insertCity] is the method to insert location data into the database
     * @param databaseLocation of Type [DatabaseLocation] representing the location data
     */
    fun insertCity(databaseLocation: DatabaseLocation){
        uiScope.launch {
            repository.insertCity(databaseLocation)
        }
    }

    /**
     * [deleteCity] is the method to insert city record from the database
     */
    fun deleteCity(){
        uiScope.launch {
            city.value?.cityName?.let { repository.deleteThis(it) }
        }
    }

}