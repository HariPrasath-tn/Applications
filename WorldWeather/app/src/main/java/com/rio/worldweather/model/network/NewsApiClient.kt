package com.rio.worldweather.model.network

import com.rio.worldweather.model.network.new_news.News
import com.rio.worldweather.model.network.news.NWeatherNews
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

//
///**
// * [NewsApiClient] is an singleton class that provides functionalities to fetch news from news api
// */
//object NewsApiClient {
//    private const val BASE_URL = "https://weather-news.p.rapidapi.com/news/lang/"
//
//    // retrofit using GsonConvertorFactory
//    val retroFit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
//        .build()
//
//    // creating the instance of the interface using retrofit library
//    // which provides the rest api method here GET()
//    val newsApiService by lazy {
//        retroFit.create(NewsApiService::class.java)
//    }
//}
//
///**
// * [NewsApiService] is an interface provided with REST API functionalities
// */
//interface NewsApiService{
//    @Headers(
//        "X-RapidAPI-Key:a873bed591msh5e30b8f5588249bp1b1cbbjsn6572ae1c4137",
//        "X-RapidAPI-Host:weather-news.p.rapidapi.com")
//    @GET("en")
//    fun getWeatherNews(): Call<NWeatherNews>
//}

object NewsApiClient {
    private const val BASE_URL = "https://newsapi.org/v2/"

    // retrofit using GsonConvertorFactory
    val retroFit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
        .build()

    // creating the instance of the interface using retrofit library
    // which provides the rest api method here GET()
    val newsApiService by lazy {
        retroFit.create(NewsApiService::class.java)
    }
}

/**
 * [NewsApiService] is an interface provided with REST API functionalities
 */
interface NewsApiService{
    @GET("everything?q=tesla&from=2022-11-11&sortBy=publishedAt&apiKey=e5121e5942e34f30b64aaadf548bf5dc")
    fun getWeatherNews(): Call<News>
}