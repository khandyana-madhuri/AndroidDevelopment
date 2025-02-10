package com.example.androiddevelopment.data.network

import com.example.androiddevelopment.data.model.WeatherResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

// visit "https://openweathermap.org/current" for the info. Goto Parameters section.

interface ApiService {
    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric" //means celsius
    ): WeatherResponse
}