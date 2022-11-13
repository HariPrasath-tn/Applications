package com.rio.worldweather.view_model

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rio.worldweather.model.database.Database
import com.rio.worldweather.model.network.weather.Data
import com.rio.worldweather.model.repository.WeatherWorldRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailedWeatherViewModel(private val context: Context): ViewModel() {
    val weather = MutableLiveData<Data>()
    val temp = MutableLiveData<String>()
    val windSpeed = MutableLiveData<String>()
    val visibility = MutableLiveData<String>()
    val airQuality = MutableLiveData<String>()
    val pressure = MutableLiveData<String>()
    val uvReadings = MutableLiveData<String>()
    val imageUrl = MutableLiveData<String>()

    private val repository:WeatherWorldRepository = WeatherWorldRepository.getInstance(this, Database.getInstance(context))
    init {
        "loa..".also {
            temp.value = it
            windSpeed.value = it
            visibility.value = it
            airQuality.value = it
            pressure.value = it
            uvReadings.value = it
        }

        CoroutineScope(Job()).launch {
            var tempVariable = repository.fetchWeatherData("12.5830", "80.05326").data[0]
            weather.postValue(tempVariable)
            temp.postValue(tempVariable.app_temp.toString()+"°")
            windSpeed.postValue(tempVariable.wind_spd.toString())
            visibility.postValue(tempVariable.vis.toString())
            airQuality.postValue(tempVariable.aqi.toString()+if(tempVariable.aqi < 70){
                "(Normal)"
            }else if(tempVariable.aqi < 120){
                "(Moderate)"
            }else{
                "(Harmful)"
            })

            pressure.postValue(tempVariable.pres.toString())
            uvReadings.postValue(tempVariable.uv.toString())
            imageUrl.postValue("https://www.weatherbit.io/static/img/icons/${tempVariable.weather.icon}.png")
        }

    }
}