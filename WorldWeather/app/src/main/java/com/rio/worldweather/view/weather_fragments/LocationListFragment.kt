package com.rio.worldweather.view.weather_fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.rio.worldweather.R
import com.rio.worldweather.databinding.FragmentLocationListBinding
import com.rio.worldweather.model.database.Database
import com.rio.worldweather.model.database.DatabaseLocation
import com.rio.worldweather.view.NewsActivity
import com.rio.worldweather.view.WeatherActivity
import com.rio.worldweather.view.adapter.LocationListAdapter
import com.rio.worldweather.view_model.FLocationListViewModelFactory
import com.rio.worldweather.view_model.LocationListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [LocationListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LocationListFragment : Fragment(),LocationListAdapter.LocationListInteraction {
    // creating a container for storing the data binding instance as late init
    private lateinit var binding: FragmentLocationListBinding
    // creating a container for view model as late init which will be initialized on onCreateView
    private lateinit var locationListViewModel:LocationListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // establishing the data binding between the layout and fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_location_list, container, false)
        var activity = requireNotNull(activity)
        locationListViewModel = ViewModelProvider(this, FLocationListViewModelFactory(activity))[LocationListViewModel::class.java]
        // assigning lifecycle owner
        binding.lifecycleOwner = this

        binding.addNewLocationButton.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_locationListFragment_to_mapsFragment)
        }

        binding.footer.newsButton.apply {
            setOnClickListener {
                Intent(activity, NewsActivity::class.java).also {
                    startActivity(it)
                }
            }
        }

        binding.footer.weatherButton.apply {
            setOnClickListener {
                Intent(activity, WeatherActivity::class.java).also {
                    startActivity(it)
                }
            }
        }

        initRecyclerView()


        // return fragment view
        return binding.root
    }

    /**
     * [onStarClicked] is the method that executes on clicking the star button in the recyclerView
     * it removes the particular location from the database
     * @param position of type Integer representing the position of the view in the recycler view
     * @param item of type DatabaseLocation representing the location data of the view at the
     * current position in the recycler view
     */
    override fun onStarClicked(position: Int, item: DatabaseLocation) {
        locationListViewModel.deleteStaredLocation(item)
    }

    /**
     * [onStarClicked] is the method that executes on clicking the location view in the recyclerView
     * it launches the fragment [DetailedWeatherFragment] for the location of the view clicked
     * @param view of type View representing the item view in the recycler view
     * @param item of type DatabaseLocation representing the location data of the view at the
     * current position in the recycler view
     */
    override fun onViewClicked(view:View, item: DatabaseLocation) {
       Navigation.findNavController(view).navigate(LocationListFragmentDirections.actionLocationListFragmentToDetailedWeatherFragment(item.lat, item.lon))
    }

    /**
     * [initRecyclerView] is the method that initializes the recyclerView for the fragment [LocationListFragment]
     * which will display the stared location in the map
     */
    private fun initRecyclerView(){
        // creating the instance of the locationListAdapter
        var adapter = LocationListAdapter(this@LocationListFragment)
        // providing the adapter to the recycler view
        binding.staredPlaceRecyclerView.adapter = adapter
        // managing the layout of the recycler view by creating the layout manager
        binding.staredPlaceRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.lifecycleOwner?.let {
            locationListViewModel.staredLocationList.observe(it, Observer{ it2 ->
                it2?.let { it1 -> adapter.submitList(it1) }
            })
        }
    }
}