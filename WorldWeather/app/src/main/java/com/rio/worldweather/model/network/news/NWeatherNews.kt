package com.rio.worldweather.model.network.news

data class NWeatherNews(
    val articles: List<Article>,
    val message: String,
    val status: Int
)