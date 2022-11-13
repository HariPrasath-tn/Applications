package com.rio.worldweather.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FDetailedWeatherViewModelFactory(private val context:Context):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DetailedWeatherViewModel::class.java)){
            return DetailedWeatherViewModel(context) as T
        }
        // Exception thrown when modelClass is not assignable from DetailedWeatherModel
        throw IllegalArgumentException("Illegal argument")
    }
}