package com.example.androiddevelopment.presentation.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.androiddevelopment.R
import com.example.androiddevelopment.data.model.WeatherResponse
import com.example.androiddevelopment.databinding.ActivityMainBinding
import com.example.androiddevelopment.presentation.viewmodel.WeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.util.Locale

class MainActivity : ComponentActivity() {

    private val weatherViewModel: WeatherViewModel by inject()
    private lateinit var binding: ActivityMainBinding
    private val apiKey = "7fc99948fbb1a8dbd841623a5f0980c3"
    private lateinit var locationPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var fusedLocationClient : FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = weatherViewModel

        enableEdgeToEdge()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        locationPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if(isGranted) {
                CoroutineScope(Dispatchers.Main).launch {
                    fetchLocation { loc->
                        if(loc == null)
                            Toast.makeText(this@MainActivity, "Failed to fetch location", Toast.LENGTH_LONG).show()
                        else
                            weatherViewModel.fetchWeather(apiKey, loc, applicationContext)
                    }
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
                fetchLocation { loc ->
                    if(loc == null)
                        Toast.makeText(this@MainActivity, "Failed to get the location", Toast.LENGTH_LONG).show()
                    else
                        weatherViewModel.fetchWeather(apiKey, loc, applicationContext)
                }
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
    fun fetchLocation(callback: (String?) -> Unit) {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_BALANCED_POWER_ACCURACY, 2000)
                .setMinUpdateIntervalMillis(1000)
                .setWaitForAccurateLocation(false)
                .build()

        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            fusedLocationClient.lastLocation.addOnSuccessListener {  location ->
                if(location == null) {
                    val locationCallback = object : LocationCallback() {
                        override fun onLocationResult(locationResult: LocationResult) {
                            Log.d("fetchLocation", "locationCallback result ${locationResult.lastLocation}")
                            locationResult.lastLocation?.let {
                                callback(getCityFromLocation(it))
                                fusedLocationClient.removeLocationUpdates(this) // Stop updates after first result
                            }
                        }
                    }
                    fusedLocationClient.requestLocationUpdates(
                        locationRequest,
                        locationCallback,
                        Looper.getMainLooper()
                    ).addOnFailureListener {
                        callback(null)
                        Log.d("requestLocation","failed to get location")
                    }
                }
                else {
                    callback(getCityFromLocation(location))
                }

            }
        }
    }

    private fun getCityFromLocation(location: android.location.Location)  : String? {
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


    @SuppressLint("ClickableViewAccessibility")
    private fun setupListeners() {
        binding.cityName.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) { // The condition checks if the touch event occurred within the bounds of the drawable.
                val drawableEnd = binding.cityName.compoundDrawables[2]
                if (drawableEnd != null && event.rawX >= (binding.cityName.right - drawableEnd.bounds.width())) {
                    binding.cityName.text?.clear()
                    return@setOnTouchListener true
                }
            }
            return@setOnTouchListener false
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
            cityName.setText(weatherData.name)
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
