package com.rio.worldweather.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rio.worldweather.exception.UnAuthorizedAccessException
import com.rio.worldweather.model.database.Database
import com.rio.worldweather.model.database.DatabaseLocation
import com.rio.worldweather.model.network.WeatherApiClient
import com.rio.worldweather.model.network.weather.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await

class WeatherWorldRepository private constructor(private val database:Database) {

    companion object{
        /**
         * @param obj key,
         * static method to get the instance of the repository weather
         */
        /*
         * obj is the instance of the class that is asking for the instance of
         * the class WeatherRepository
         */
        fun getInstance(obj:Any, database: Database):WeatherWorldRepository{
            if(obj is ViewModel){
                return WeatherWorldRepository(database)
            }
            throw UnAuthorizedAccessException("Access Denied")
        }
    }

    /**
     * [fetchWeatherData] suspended method fetchWeatherData fetch the current weather data
     */
    suspend fun fetchWeatherData(lat:String, lon:String): Weather {
        print("Test")
        return withContext(Dispatchers.IO) {
            WeatherApiClient.weatherApiService.fetchAll(lat, lon).await()
        }
    }

    /**
     * [fetchCities] suspended method that fetches all the stared cities from the database
     * @return LiveData<List<String>>
     */
    suspend fun fetchCities():List<String>{
        return withContext(Dispatchers.IO){
            database.locationDAO.getCities()
        }
    }

//    /**
//     * [fetchAllFromDb] suspended method that fetches all the stared cities details from the database
//     * @return LiveData<DatabaseLocation>
//     */
//    suspend fun fetchAllFromDb():LiveData<List<DatabaseLocation>>{
//        return withContext(Dispatchers.IO){
//            database.locationDAO.getAll().observe()
//            println("\n\n\n\n\n${}")
//            database.locationDAO.getAll()
//        }
//    }
    val databaseLocationList = database.locationDAO.getAll()


    /**
     * [insertCity] suspended method insert location details into the database
     * @param databaseLocation of type DatabaseLocation representing location details
     * @return unit
     */
    fun insertCity(databaseLocation: DatabaseLocation){
        database.locationDAO.insert(databaseLocation)
    }

    /**
     * [deleteThis] is the method that deletes the given data from the database
     * @param city of type String representing the city record to be deleted from the database
     */
    fun deleteThis(city:String){
        database.locationDAO.delete(city)
    }
}