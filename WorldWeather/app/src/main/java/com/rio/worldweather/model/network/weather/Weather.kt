package com.rio.worldweather.model.network.weather

data class Weather(
    val count: Int,
    val data: List<Data>
)