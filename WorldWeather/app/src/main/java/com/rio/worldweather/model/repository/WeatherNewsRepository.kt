package com.rio.worldweather.model.repository

import com.rio.worldweather.model.network.NewsApiClient
import com.rio.worldweather.model.network.news.NWeatherNews
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await

/**
 * [WeatherNewsRepository] is a repository class that provides the functionality to fetch
 * news data from the api
 */
class WeatherNewsRepository {
    /**
     * [getWeatherNews] suspend function that returns the api response
     * of weather news as a dataclass of news headlines, urls
     */
    suspend fun getWeatherNews():NWeatherNews{
        return withContext(Dispatchers.IO){
            // using await() method to change the deferred value into normal one
            NewsApiClient.newsApiService.getWeatherNews().await()
        }
    }
}