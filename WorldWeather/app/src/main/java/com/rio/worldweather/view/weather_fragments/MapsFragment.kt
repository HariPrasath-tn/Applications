package com.rio.worldweather.view.weather_fragments

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.rio.worldweather.R
import com.rio.worldweather.databinding.ActivityFooterBinding.inflate
import com.rio.worldweather.databinding.FragmentMapsBinding
import com.rio.worldweather.model.database.DatabaseLocation
import com.rio.worldweather.view_model.FMapViewModelFactory
import com.rio.worldweather.view_model.MapViewModel

class MapsFragment : Fragment(), GoogleMap.OnMapClickListener,
    GoogleMap.OnMarkerClickListener {
    private lateinit var mMap:GoogleMap
    private lateinit var binding: FragmentMapsBinding
    private val mapViewModel: MapViewModel by lazy{
        var activity = requireNotNull(activity)
        ViewModelProvider(this, FMapViewModelFactory(activity))[MapViewModel::class.java]
    }
    private var marker: Marker? = null

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        // assigning GoogleMap instance to mMap
        mMap = googleMap

        // specifying that new definition has been provided for onMapClick
        mMap.setOnMapClickListener(this)
        // specifying that new definition has been provided for onMarkerClick
        mMap.setOnMarkerClickListener(this)

        binding.lifecycleOwner?.let {
            mapViewModel.staredCitiesLocation.observe(it, Observer { it2->

                // info new user
                if(it2.isEmpty()){
                    val toast = Toast(requireActivity().applicationContext)
                    toast.duration = Toast.LENGTH_LONG
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
                    toast.view = layoutInflater.inflate(R.layout.toast_layout, requireActivity().findViewById(R.id.toastLayout))
                    toast.show()
                }

                for(city in it2){
                    mMap.addMarker(MarkerOptions().position(LatLng(city.lat.toDouble(), city.lon.toDouble())).title(city.cityName))
                }
            })
        }
        // this statement moves the camera to the given location
        mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(12.8053, 80.0123)))
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_maps, container, false)
        binding.floatingActionButton2
        binding.apply {
            mapViewData = mapViewModel

            actionBar.visibility = View.GONE
            floatingActionButton.visibility = View.GONE
            floatingActionButton2?.visibility  = View.GONE

            // setting onclick listener for the floating button
            // button to bookmark the location
            floatingActionButton.setOnClickListener {
                mapViewModel.city.value?.let { it1 -> mapViewModel.insertCity(it1) }
                setViewForFav()
            }

            // setting onclick listener for the floating button
            // button to remove the location from bookmark
            floatingActionButton2.setOnClickListener {
                mapViewModel.deleteCity()
                setViewForNotFav()
            }
        }
        binding.lifecycleOwner = this
        mapViewModel.fetchCitiesFromDB()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onMapClick(p0: LatLng) {
        // using kotlin smart cast for control flow
        var newMarker = marker

        // if marker is null create a new maker
        // else just change the position of the already existing marker
        if(newMarker == null)
            newMarker = mMap.addMarker(MarkerOptions().position(p0).title("Loading"))
        else
            newMarker.position = p0

        // assigning the newMarker to marker
        marker = newMarker
        mapViewModel.apply {
            fetchCitiesFromDB()
            getCity(p0.latitude.toString(), p0.longitude.toString())

            city.observe(binding.lifecycleOwner!!, Observer {
                var city = it.cityName
                marker?.title = city
                binding.actionBar.visibility = View.VISIBLE
                mapViewModel.staredCities.observe(binding.lifecycleOwner!!){
                    if(mapViewModel.staredCities.value?.contains(city) == true){
                        setViewForFav()
                    }else {
                        setViewForNotFav()
                    }
                }

            })
        }
        mMap.setMaxZoomPreference(mMap.maxZoomLevel)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(p0))
    }

    override fun onMarkerClick(p0: Marker): Boolean {
        binding.apply {
            var city = p0.title.toString()
            var latLon = p0.position
            actionBar.visibility = View.VISIBLE
            cityName.text = city
            setViewForFav()
            mapViewModel.city.value = DatabaseLocation(city, latLon.latitude.toString(), latLon.longitude.toString())
        }

        return false
    }

    /**
     * [setViewForFav] is the method that set the view for the already stared place
     */
    private fun setViewForFav(){
        binding.floatingActionButton.visibility = View.GONE
        binding.floatingActionButton2.visibility = View.VISIBLE
    }

    /**
     * [setViewForNotFav] is the method that set the view for the un stared place
     */
    private fun setViewForNotFav(){
        binding.floatingActionButton2.visibility = View.GONE
        binding.floatingActionButton.visibility = View.VISIBLE
    }
}