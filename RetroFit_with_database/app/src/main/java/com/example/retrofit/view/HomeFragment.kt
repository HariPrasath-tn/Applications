package com.example.retrofit.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofit.R
import com.example.retrofit.adapter.ViewAdapter
import com.example.retrofit.databinding.FragmentHomeBinding
import com.example.retrofit.model_view.HomeFragmentViewModel
import com.example.retrofit.model_view.HomeViewModelFactory

/**
 * HomeFragment is the Fragment class that extends app that acts as ont of the components
 * (layer over the activity) of the Activity class HomeFragmentHolderActivity
 *
 */
class HomeFragment : Fragment() {
    // creating container for the instance of the binding class FragmentHomeBinding
    private lateinit var binding:FragmentHomeBinding
    // creating lazy instance of the binding class homeFragmentViewModel
    private val homeFragmentViewModel: HomeFragmentViewModel by lazy{
        val activity = requireNotNull(this.activity){ "Ithu summa itha kandukaathinga" }
        // Factory is used to pass the arguments into the viewModel class
        ViewModelProvider(this, HomeViewModelFactory(activity.application))[HomeFragmentViewModel::class.java]
    }

    // override method creates the fragment view once the Activity layer is redirected to this fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // establishing the binding between the xml view and the fragment class
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        // assigning the instance of the class homeViewFragmentViewModel to the homeViewModel in the
        // xml variable
        binding.homeViewModel = homeFragmentViewModel
        binding.lifecycleOwner = this

        // creating the adapter instance
        val adapter = activity?.applicationContext?.let {
            ViewAdapter(
                it
            )}

        adapter?.items = homeFragmentViewModel.characterList
//        // observing the viewModel variable for changes to update
//        homeFragmentViewModel.characterList.observe(viewLifecycleOwner, Observer{
//            adapter?.items =it
//        })

        // assigning the adapter to the recyclerView's adapter
        binding.myRecyclerView.adapter = adapter
        // defining the layout manager for the recycler view
        binding.myRecyclerView.layoutManager = LinearLayoutManager(activity)

        return binding.root
    }
}