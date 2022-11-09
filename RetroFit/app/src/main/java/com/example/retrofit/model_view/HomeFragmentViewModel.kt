package com.example.retrofit.model_view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofit.network.ApiClient
import com.example.retrofit.network.CharacterResponse
import retrofit2.Call
import retrofit2.Response

class HomeFragmentViewModel : ViewModel() {
    val _response = MutableLiveData<String>()


    fun getDataList():List<com.example.retrofit.network.Character>{
        var list = listOf<com.example.retrofit.network.Character>()
        val client = ApiClient.apiService
        client.fetchCharacters("1")
            .enqueue(object : retrofit2.Callback<CharacterResponse> {
                override fun onResponse(
                    call: Call<CharacterResponse>,
                    response: Response<CharacterResponse>
                ) {
                    if (response.isSuccessful) {
                        list += response.body()!!.result
                        _response.value = ""
                    }
                }

                override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
                    _response.value = "Failure: " + t.message
                }
            })
        return list
    }

}