package com.rio.worldweather.view.news_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.rio.worldweather.R
import com.rio.worldweather.databinding.FragmentDetailedNewsBinding

/**
 * A simple [Fragment] subclass.
 * Use the [DetailedNewsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailedNewsFragment : Fragment() {
    private lateinit var binding: FragmentDetailedNewsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detailed_news, container, false)
        binding.lifecycleOwner = this

        binding.newsWebView.webViewClient = WebViewClient()
        val args : WeatherNewsFragmentArgs by navArgs()
        binding.newsWebView.loadUrl(args.newsUrl)

        return binding.root
    }
}