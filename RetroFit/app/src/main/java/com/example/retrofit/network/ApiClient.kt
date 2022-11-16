package com.example.retrofit.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object ApiClient {
    /**
     * base url
     */
    private const val BASE_URL = "https://rickandmortyapi.com/api/"

    // creating moshi instance
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    // creating the instance of the retrofit
    private val retrofit:Retrofit by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }


    /**
     * creating the object of the ApiService as lazy
     */
    val apiService:ApiService by lazy{
        retrofit.create(ApiService::class.java)
    }
}

/**
 * [ApiService] interface to define how Retrofit talks to the service using the GetMethod
 */
interface ApiService{
    /**
     * [fetchCharacters] is the data fetching method that is annotated with
     * GET annotation passing the character as path and page as the Query
     */
    @GET("character")
    fun fetchCharacters(@Query("page") page:String):Call<CharacterResponse>
}