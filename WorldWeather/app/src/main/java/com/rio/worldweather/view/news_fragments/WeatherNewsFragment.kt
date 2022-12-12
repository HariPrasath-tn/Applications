package com.rio.worldweather.view.news_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.rio.worldweather.R
import com.rio.worldweather.databinding.FragmentWeatherNewsBinding
import com.rio.worldweather.model.network.new_news.Article
import com.rio.worldweather.view.adapter.NewsListAdapter
import com.rio.worldweather.view_model.FWeatherNewsViewModelFactory
import com.rio.worldweather.view_model.WeatherNewsViewModel
import kotlinx.coroutines.CoroutineExceptionHandler

class WeatherNewsFragment : Fragment(), NewsListAdapter.NewsListInteraction {
    private lateinit var binding: FragmentWeatherNewsBinding
    private lateinit var weatherNewsViewModel: WeatherNewsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_weather_news, container, false)
        binding.lifecycleOwner = this
        weatherNewsViewModel = ViewModelProvider(this, FWeatherNewsViewModelFactory(handler))[WeatherNewsViewModel::class.java]

        initRecyclerView()
        return binding.root
    }

    override fun onLoadNewsClicked(view:View, item: Article) {
        Navigation.findNavController(view).navigate(WeatherNewsFragmentDirections.actionWeatherNewsFragmentToDetailedNewsFragment(item.url))
    }

    override fun onViewClicked(view: View, item: Article) {
        binding.newsPopView.visibility = View.VISIBLE
        binding.newPopImage.load(item.urlToImage)
        binding.newsPopHeadLine.text = item.title
    }

    private fun initRecyclerView(){
        var adapter = NewsListAdapter(this@WeatherNewsFragment)
        binding.newRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.newRecyclerView.adapter = adapter
        binding.lifecycleOwner?.let {
            weatherNewsViewModel.weatherNews.observe(it, Observer{it1->
                binding.progressBar?.visibility = View.GONE
                adapter.submitList(it1.articles)
            })
        }
    }

    // creating Coroutine exception handler and handling Exception thrown during the coroutine execution
    private val handler: CoroutineExceptionHandler = CoroutineExceptionHandler { _, _ ->
        requireActivity().runOnUiThread {
            binding.serverDownInfoLayout?.visibility = View.VISIBLE
        }
    }
}