package com.example.retrofit.network

import androidx.compose.ui.layout.ScaleFactor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET


private const val BASE_URL = "https://mars.udacity.com/"
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface ApiService{
    @GET("realestate")
    fun getProperties():
            Call<String>
}

object Api{
    val retrofitService:ApiService by lazy{
        retrofit.create(ApiService::class.java)
    }
}