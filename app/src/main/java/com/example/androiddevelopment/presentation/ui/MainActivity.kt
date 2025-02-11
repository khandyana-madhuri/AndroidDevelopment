package com.example.androiddevelopment.presentation.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.androiddevelopment.R
import com.example.androiddevelopment.data.model.WeatherResponse
import com.example.androiddevelopment.databinding.ActivityMainBinding
import com.example.androiddevelopment.presentation.viewmodel.WeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.koin.android.ext.android.inject
import java.util.Locale

class MainActivity : ComponentActivity() {

    private val weatherViewModel: WeatherViewModel by inject()
    private lateinit var binding: ActivityMainBinding
    private val apiKey = "7fc99948fbb1a8dbd841623a5f0980c3"
    private lateinit var locationPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var fusedLocationClient : FusedLocationProviderClient
    private lateinit var city: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        locationPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if(isGranted) {
                CoroutineScope(Dispatchers.Main).launch {
                    city = fetchLocation().toString()
                    weatherViewModel.fetchWeather(apiKey, city, applicationContext)
                    Log.d("MainActivity CityName", city)
                }
            }
            else
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show()
        }


        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // If permission is not granted, ask the permission.
            requestLocationPermission()
        }
        else {
            // If permission is granted, fetch the location.
            CoroutineScope(Dispatchers.Main).launch {
                city = fetchLocation().toString()
                weatherViewModel.fetchWeather(apiKey, city, applicationContext)
                Log.d("MainActivity CityName", city)
            }
        }
        setupObservers()
        setupListeners()
    }

    private fun requestLocationPermission() {
        locationPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    // Function to fetch city name
    @SuppressLint("MissingPermission")
    suspend fun fetchLocation(): String? {
        val location = fusedLocationClient.lastLocation.await()

        if (location == null) {
            Log.d("MainActivity", "Location is null. Consider requesting location updates.")
            return null
        }

        val geocoder = Geocoder(this, Locale.getDefault())
        return try {
            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                val address = addresses[0]
                // Try getting the locality, with fallbacks to other fields
                val cityName = address.locality ?: address.subAdminArea ?: address.adminArea
                cityName
            } else {
                Log.d("MainActivity", "Failed to get address from Geocoder.")
                null
            }
        } catch (e: Exception) {
            Log.e("MainActivity", "Geocoder failed: ${e.message}")
            null
        }
    }

    private fun setupObservers() {
        weatherViewModel.weatherData.observe(this) { weatherData ->
            if (weatherData != null) {
                updateUI(weatherData)
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
                Toast.makeText(this, "Please enter a city name.", Toast.LENGTH_LONG).show()
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
}
