package com.rio.worldweather.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rio.worldweather.model.network.new_news.News
import com.rio.worldweather.model.repository.WeatherNewsRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class WeatherNewsViewModel(private val handler: CoroutineExceptionHandler) : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(job)
//    val weatherNews = MutableLiveData<NWeatherNews>()
    val weatherNews = MutableLiveData<News>()

    init {
        uiScope.launch(handler) {
            weatherNews.postValue(WeatherNewsRepository().getWeatherNews())
        }
    }
}