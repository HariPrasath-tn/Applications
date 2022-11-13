package com.rio.worldweather.view.weather_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.rio.worldweather.R
import com.rio.worldweather.databinding.FragmentDetailedWeatherBinding
import com.rio.worldweather.view_model.DetailedWeatherViewModel
import com.rio.worldweather.view_model.FDetailedWeatherViewModelFactory

/**
 * A simple [Fragment] subclass.
 * Use the [DetailedWeatherFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailedWeatherFragment : Fragment() {
    private lateinit var binding:FragmentDetailedWeatherBinding
    private lateinit var detailedWeatherViewModel: DetailedWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detailed_weather, container, false)
        binding.lifecycleOwner = this
        var activity = requireNotNull(activity)
        detailedWeatherViewModel = ViewModelProvider(this, FDetailedWeatherViewModelFactory(activity))[DetailedWeatherViewModel::class.java]

        binding.apply {
            detailedWeatherViewModel.also {
                detailedWeatherTop.weatherViewModel = it
                detailedWeatherBottom.weatherViewModel = it
                detailedWeatherMiddle.weatherViewModel = it
            }
        }

        detailedWeatherViewModel.imageUrl.observe(viewLifecycleOwner, Observer {
            binding.detailedWeatherTop.weatherIv.load(it.toString())
        })


        return binding.root
    }
}