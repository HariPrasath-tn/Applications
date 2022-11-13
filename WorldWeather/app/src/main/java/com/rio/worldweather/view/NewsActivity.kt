package com.rio.worldweather.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.rio.worldweather.R
import com.rio.worldweather.databinding.ActivityNewsBinding


class NewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news)
        binding.footer.newsButton.setOnClickListener {
            Intent(this, NewsActivity::class.java).also{
                startActivity(it)
            }
        }
        binding.footer.weatherButton.setOnClickListener {
            Intent(this, WeatherActivity::class.java).also{
                startActivity(it)
            }
        }
    }
}