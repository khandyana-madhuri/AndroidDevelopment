package com.example.androiddevelopment.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.androiddevelopment.R
import com.example.androiddevelopment.databinding.ActivityMainBinding
import com.example.androiddevelopment.presentation.viewmodel.WeatherViewModel
import org.koin.android.ext.android.inject


class MainActivity : ComponentActivity() {
    private val weatherViewModel: WeatherViewModel by inject()
    private lateinit var binding: ActivityMainBinding
    private val apiKey = "7fc99948fbb1a8dbd841623a5f0980c3"

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        weatherViewModel.fetchWeather(
            apiKey,
            binding.cityName.text.toString().trim(),
            applicationContext
        )
        updateUI(binding)

        binding.clear.setOnClickListener {
            binding.cityName.text?.clear()
        }

        binding.search.setOnClickListener {
            weatherViewModel.fetchWeather(
                apiKey,
                binding.cityName.text.toString().trim(),
                applicationContext
            )
            updateUI(binding)
        }
    }

    private fun updateUI(binding: ActivityMainBinding) {
        binding.apply {
            weatherViewModel.weatherData.observe(this@MainActivity) {
                city.text = it?.name
                temperature.text = it?.main?.temp.toString().plus( "°C")
                humidity.text = it?.main?.humidity.toString().plus("%")
                it?.weather?.forEach { desc->
                    description.text = desc.description
                }
                if (description.text.contains("cloud")) {
                    main.background = ContextCompat.getDrawable(this@MainActivity,
                        R.drawable.cloudy_bg
                    )
                    image.visibility = View.VISIBLE
                    image.setImageResource(R.drawable.cloudy)
                }
                else if (description.text.contains("rain")) {
                    image.visibility = View.VISIBLE
                    main.background = ContextCompat.getDrawable(this@MainActivity,
                        R.drawable.rainy_bg
                    )
                    image.setImageResource(R.drawable.rainy)
                }
                else if (description.text.contains("snow")) {
                    image.visibility = View.VISIBLE
                    main.background = ContextCompat.getDrawable(this@MainActivity,
                        R.drawable.snow_bg
                    )
                    image.setImageResource(R.drawable.snowy)
                }
                else if (description.text.contains("sun")) {
                    image.visibility = View.VISIBLE
                    main.background = ContextCompat.getDrawable(this@MainActivity,
                        R.drawable.sunny_bg
                    )
                    image.setImageResource(R.drawable.sunny)
                }
                else {
                    main.background = ContextCompat.getDrawable(this@MainActivity,
                        R.drawable.weather
                    )
                    image.visibility = View.INVISIBLE
                }
            }
        }
    }
}


