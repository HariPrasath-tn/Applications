package com.example.retrofit.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET


/**
 * base url
 */
private const val BASE_URL = "https://mars.udacity.com/"

// creating the instance of the retrofit
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()


interface ApiService{
    /**
     * [getProperties] is the data fetching method that is annotated with
     * GET annotation passing the realestate as path
     */
    @GET("realestate")
    fun getProperties():
            Call<String>
}

object Api{
    /**
     * creating the object of the ApiService as lazy
     */
    val retrofitService:ApiService by lazy{
        retrofit.create(ApiService::class.java)
    }
}