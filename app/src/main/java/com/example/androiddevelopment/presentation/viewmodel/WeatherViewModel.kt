package com.example.androiddevelopment.presentation.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androiddevelopment.data.Repository.WeatherRepo
import com.example.androiddevelopment.data.model.WeatherResponse
import kotlinx.coroutines.launch

class WeatherViewModel(private val weatherRepo: WeatherRepo) : ViewModel() {
    private var _weatherData : MutableLiveData<WeatherResponse?> = MutableLiveData(null)
    val weatherData : LiveData<WeatherResponse?> = _weatherData
    val showUI = ObservableBoolean(false)

    fun fetchWeather(apiKey: String, city: String, context: Context) {
        viewModelScope.launch {
            try {
                val response = weatherRepo.getCurrentWeather(city, apiKey)
                Log.d("weather ", response.toString())
                _weatherData.postValue(response)
                showUI.set(true)
            }
            catch (e: Exception) {
                Toast.makeText(context, "Please enter a valid city name", Toast.LENGTH_LONG).show()
                e.printStackTrace()
            }
        }
    }
}