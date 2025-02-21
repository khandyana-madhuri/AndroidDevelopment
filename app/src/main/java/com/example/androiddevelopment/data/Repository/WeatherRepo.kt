package com.example.androiddevelopment.data.Repository

import com.example.androiddevelopment.data.model.WeatherResponse
import com.example.androiddevelopment.data.network.ApiService

class WeatherRepo(private val apiService: ApiService) {
    suspend fun getCurrentWeather(city: String, apiKey: String) : WeatherResponse {
        return apiService.getWeather(city, apiKey)
    }
}