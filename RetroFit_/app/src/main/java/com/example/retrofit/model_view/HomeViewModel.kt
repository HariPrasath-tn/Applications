package com.example.retrofit.model_view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofit.network.Api
import okhttp3.Response
import retrofit2.Call
import retrofit2.Callback

class HomeViewModel :ViewModel(){
    val homeResponse = MutableLiveData<String>()

    init {
        // initializing the retrofit get request
        getMarsRealEstateProperties()
    }

    private fun getMarsRealEstateProperties(){
        try{
            Api.retrofitService.getProperties().enqueue(object:Callback<String>{
                /*
                 * validating the responses
                 * if it is success then assigns the list of data to the mutable live data
                 * else throws the exception which will be handled by the catch block
                 */
                override fun onResponse(call: Call<String>, response: retrofit2.Response<String>) {
                    homeResponse.value = response.body()
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    throw Exception("Unable to fetch the data ")
                }
            })
        }catch (e:Exception){
            homeResponse.value = "Failure: "+e.message
        }


    }
}