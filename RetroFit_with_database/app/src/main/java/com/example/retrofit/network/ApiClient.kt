package com.example.retrofit.network

import com.example.retrofit.network.data_class.NAnimeCharacter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * APIClient is the class that as like a client side to the server
 *  > USAGE: fetch the data respective to the query provided with the URI
 *
 *  methodology:
 *      > creating moshi instance inorder to convert the JSON to kotlin Object
 *      > build the retrofit instance as lazy
 *      > create the  instance of the interface ApiServices as lazy
 */
object ApiClient {

    // base url
    private const val BASE_URL = "https://rickandmortyapi.com/api/"

    // building the moshi converter
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    // instance of the retrofit
    private val retrofit:Retrofit by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    // instance of the apiService interface using the retrofit libraries
    val apiService:ApiService by lazy{
        retrofit.create(ApiService::class.java)
    }
}

/**
 * interface to set API Functionalities
 *  > fetches the json data for the respective url with the query page number under the branch
 *  character which i mentioned as the parameter for the @GET annotation
 */
interface ApiService{
    @GET("character")
    fun fetchCharacters(@Query("page") page:String):Call<NAnimeCharacter>
}