package com.example.retrofit.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit.R
import com.example.retrofit.adapter.ViewAdapter
import com.example.retrofit.database.AnimeCharacter
import com.example.retrofit.databinding.FragmentHomeBinding
import com.example.retrofit.model_view.HomeFragmentViewModel
import com.example.retrofit.model_view.HomeViewModelFactory


/**
 * HomeFragment is the Fragment class that extends app that acts as ont of the components
 * (layer over the activity) of the Activity class HomeFragmentHolderActivity
 *
 */
class HomeFragment : Fragment(), ViewAdapter.Interaction {
    // creating container for the instance of the binding class FragmentHomeBinding
    private lateinit var binding:FragmentHomeBinding

    // webSitePage
    private var page:Int = 1
    // creating lazy instance of the binding class homeFragmentViewModel
    private val homeFragmentViewModel: HomeFragmentViewModel by lazy{
        val activity = requireNotNull(this.activity)
        // Factory is used to pass the arguments into the viewModel class
        ViewModelProvider(this, HomeViewModelFactory(activity.application))[HomeFragmentViewModel::class.java]
    }

    private lateinit var viewAdapter: ViewAdapter

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

        // observing the viewModel variable for changes to update
        homeFragmentViewModel.characterList.observe(viewLifecycleOwner, Observer{
            initRecyclerView()
            viewAdapter.submitList(it)
        })
        binding.myRecyclerView.layoutManager = LinearLayoutManager(activity)

        return binding.root
    }

    /**
     * [initRecyclerView] is the method that setUp recyclerView for the application
     */
    private fun initRecyclerView(){
        binding.myRecyclerView.apply {
            // defining layout manager for the recycler view
            layoutManager = GridLayoutManager(activity, 3)
            // creating the instance of the ViewAdapter
            viewAdapter = ViewAdapter(this@HomeFragment)
            adapter = viewAdapter
        }
    }

    /*
     * method that response to the onclick of the view
     */
    override fun onItemSelected(position: Int, item: AnimeCharacter) {
        Toast.makeText(activity, "$position   =   ${item.name}", Toast.LENGTH_SHORT).show()
    }
}



//        binding.myRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//                if (!recyclerView.canScrollVertically(1)) {
//                    homeFragmentViewModel.apply {
//                        loadCharacters(page++)
//                        loadCharacterFromDb()
//                    }
//                }
//            }
//        })