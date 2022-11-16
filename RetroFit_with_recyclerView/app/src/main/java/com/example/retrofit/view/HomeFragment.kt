package com.example.retrofit.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
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
        binding.lifecycleOwner = this

        // assigning the layoutManger of the recyclerview
        binding.myRecyclerView.layoutManager = GridLayoutManager(activity,3)

        // Observing the response that is been fetched from the retrofit client
        homeFragmentViewModel._response.observe(viewLifecycleOwner, Observer {
            // getting the activity context if it is not null
            var activity = requireNotNull(activity)
            // using let block to prevent null exception
            it?.let{
                val viewAdapter: ViewAdapter? = ViewAdapter(activity, it)
                binding.myRecyclerView.adapter = viewAdapter
            }

            // if block will get executed when retrofit unable to fetch the data
            // and it toast the user telling it is unable to fetch the data
            if(it == null){
                Toast.makeText(activity, "Failed to fetch the Data", Toast.LENGTH_SHORT).show()
            }

        })
        return binding.root
    }
}