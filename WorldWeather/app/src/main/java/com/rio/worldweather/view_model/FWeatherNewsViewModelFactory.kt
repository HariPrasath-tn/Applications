package com.rio.worldweather.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineExceptionHandler

class FWeatherNewsViewModelFactory(private val handler: CoroutineExceptionHandler):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(WeatherNewsViewModel::class.java)){
            return WeatherNewsViewModel(handler) as T
        }
        throw IllegalArgumentException("(FWeatherNewsViewModelFactory)-IllegalArgumentException")
    }
}