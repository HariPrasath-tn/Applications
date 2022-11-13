package com.rio.worldweather.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rio.worldweather.model.network.news.NWeatherNews
import com.rio.worldweather.model.repository.WeatherNewsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class WeatherNewsViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(job)
    val weatherNews = MutableLiveData<NWeatherNews>()

    init {
        uiScope.launch {
            weatherNews.postValue(WeatherNewsRepository().getWeatherNews())
        }
    }
}