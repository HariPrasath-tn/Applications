package com.example.retrofit.overview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofit.network.Api
import okhttp3.Response
import retrofit2.Call
import retrofit2.Callback

class HomeViewModel :ViewModel(){
    val homeResponse = MutableLiveData<String>()

    init {
        getMarsRealEstateProperties()
    }

    private fun getMarsRealEstateProperties(){
        Api.retrofitService.getProperties().enqueue(object:Callback<String>{
            override fun onResponse(call: Call<String>, response: retrofit2.Response<String>) {
                homeResponse.value = response.body()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                homeResponse.value = "Failure: "+t.message
            }
        })
    }
}