package com.example.retrofit.model_view

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofit.network.ApiClient
import com.example.retrofit.network.CharacterResponse
import retrofit2.Call
import retrofit2.Response

class HomeFragmentViewModel : ViewModel() {
    // creating the the variable response to store the response from the internet
    val _response = MutableLiveData<List<com.example.retrofit.network.Character>?>()

    init{
        // initializing the retrofit get request
        getDataList()
    }

    private fun getDataList(){
        // creating the ApiClient instance
        val client = ApiClient.apiService

        try {
            // fetching the data from the page 2
            client.fetchCharacters("2")
                .enqueue(object : retrofit2.Callback<CharacterResponse> {
                    /*
                     * validating the responses
                     * if it is success then assigns the list of data to the mutable live data
                     * else throws the exception which will be handled by the catch block
                     */
                    override fun onResponse(
                        call: Call<CharacterResponse>,
                        response: Response<CharacterResponse>
                    ) {
                        if (response.isSuccessful) {
                            _response.value = response.body()?.result
                        }
                    }

                    override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
                        throw Exception("Unable to fetch the data ")
                    }
                })
        }catch (e: Exception){
            // here the value for response is assigned to null telling the ui that unable to fetch the data
            _response.value = null
        }
    }

}