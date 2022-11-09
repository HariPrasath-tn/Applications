package com.example.retrofit.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofit.R
import com.example.retrofit.adapter.ViewAdapter
import com.example.retrofit.databinding.FragmentHomeBinding
import com.example.retrofit.model_view.HomeFragmentViewModel
import com.example.retrofit.network.ApiClient
import com.example.retrofit.network.CharacterResponse
import retrofit2.Call
import retrofit2.Response

class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding
    private lateinit var homeFragmentViewModel: HomeFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        homeFragmentViewModel = ViewModelProvider(this)[HomeFragmentViewModel::class.java]
        binding.homeViewModel = homeFragmentViewModel
        val client = ApiClient.apiService.fetchCharacters("2")
            .enqueue(object : retrofit2.Callback<CharacterResponse> {
                override fun onResponse(
                    call: Call<CharacterResponse>,
                    response: Response<CharacterResponse>
                ) {
                    if (response.isSuccessful) {
                        binding.myRecyclerView.layoutManager = GridLayoutManager(activity,3)
                        val viewAdapter: ViewAdapter? =
                            activity?.let { response.body()?.result?.let { it1 ->
                                ViewAdapter(it.applicationContext,
                                    it1
                                )
                            } }
                        binding.myRecyclerView.adapter = viewAdapter
                    }
                }

                override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
                    Toast.makeText(activity, "Failure: " + t.message, Toast.LENGTH_SHORT).show()
                }
            })

        binding.lifecycleOwner = this
        return binding.root
    }
}