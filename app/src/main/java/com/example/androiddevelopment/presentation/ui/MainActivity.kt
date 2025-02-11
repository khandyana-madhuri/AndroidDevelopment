package com.example.androiddevelopment.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.androiddevelopment.R
import com.example.androiddevelopment.data.model.WeatherResponse
import com.example.androiddevelopment.databinding.ActivityMainBinding
import com.example.androiddevelopment.presentation.viewmodel.WeatherViewModel
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val weatherViewModel: WeatherViewModel by inject()
    private lateinit var binding: ActivityMainBinding
    private val apiKey = "7fc99948fbb1a8dbd841623a5f0980c3"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        weatherViewModel.weatherData.observe(this) { weatherData ->
            if (weatherData != null) {
                updateUI(weatherData)
            } else {
                showError("Failed to fetch weather data.")
            }
        }
    }

    private fun setupListeners() {
        binding.clear.setOnClickListener {
            binding.cityName.text?.clear()
        }

        binding.search.setOnClickListener {
            val city = binding.cityName.text.toString().trim()
            if (city.isEmpty()) {
                showError("Please enter a city name.")
            } else {
                weatherViewModel.fetchWeather(apiKey, city, applicationContext)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(weatherData: WeatherResponse) {
        binding.apply {
            city.text = weatherData.name
            temperature.text = weatherData.main.temp.let { "$it°C" }
            humidity.text = weatherData.main.humidity.let { "$it%" }
            description.text = weatherData.weather.firstOrNull()?.description ?: "N/A"

            // Update background and image based on weather description
            updateWeatherBackgroundAndImage(description.text.toString())
        }
    }

    private fun updateWeatherBackgroundAndImage(description: String) {
        val (backgroundRes, imageRes, isImageVisible) = when {
            description.contains("cloud", ignoreCase = true) -> {
                Triple(R.drawable.cloudy_bg, R.drawable.cloudy, true)
            }
            description.contains("rain", ignoreCase = true) -> {
                Triple(R.drawable.rainy_bg, R.drawable.rainy, true)
            }
            description.contains("snow", ignoreCase = true) -> {
                Triple(R.drawable.snow_bg, R.drawable.snowy, true)
            }
            description.contains("sun", ignoreCase = true) -> {
                Triple(R.drawable.sunny_bg, R.drawable.sunny, true)
            }
            else -> Triple(R.drawable.weather, 0, false)
        }

        binding.apply {
            main.background = ContextCompat.getDrawable(this@MainActivity, backgroundRes)
            if (isImageVisible) {
                image.visibility = View.VISIBLE
                image.setImageResource(imageRes)
            } else {
                image.visibility = View.INVISIBLE
            }
        }
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
