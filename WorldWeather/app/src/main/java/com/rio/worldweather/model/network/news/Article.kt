package com.rio.worldweather.model.network.news

data class Article(
    val imageUrl: String,
    val lang: String,
    val source: String,
    var title: String,
    val url: String
)