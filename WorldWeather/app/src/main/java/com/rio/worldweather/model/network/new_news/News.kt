package com.rio.worldweather.model.network.new_news

data class News(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)