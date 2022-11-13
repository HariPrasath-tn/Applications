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
import com.rio.worldweather.model.network.news.Article
import com.rio.worldweather.view.adapter.NewsListAdapter
import com.rio.worldweather.view_model.WeatherNewsViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [WeatherNewsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WeatherNewsFragment : Fragment(), NewsListAdapter.NewsListInteraction {
    private lateinit var binding: FragmentWeatherNewsBinding
    private lateinit var weatherNewsViewModel: WeatherNewsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_weather_news, container, false)
        binding.lifecycleOwner = this
        weatherNewsViewModel = ViewModelProvider(this)[WeatherNewsViewModel::class.java]

        initRecyclerView()
        return binding.root
    }

    override fun onLoadNewsClicked(view:View, item: Article) {
        Navigation.findNavController(view).navigate(WeatherNewsFragmentDirections.actionWeatherNewsFragmentToDetailedNewsFragment(item.url))
    }

    override fun onViewClicked(view: View, item: Article) {
        binding.newsPopView.visibility = View.VISIBLE
        binding.newPopImage.load(item.imageUrl)
        binding.newsPopHeadLine.text = item.title
    }

    fun initRecyclerView(){
        var adapter = NewsListAdapter(this@WeatherNewsFragment)
        binding.newRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.newRecyclerView.adapter = adapter
        binding.lifecycleOwner?.let {
            weatherNewsViewModel.weatherNews.observe(it, Observer{it1->
                adapter.submitList(it1.articles)
            })
        }
    }
}