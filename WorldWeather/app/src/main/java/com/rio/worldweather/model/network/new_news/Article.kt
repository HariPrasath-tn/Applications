package com.rio.worldweather.model.network.new_news

data class Article(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    var title: String,
    val url: String,
    val urlToImage: String
)